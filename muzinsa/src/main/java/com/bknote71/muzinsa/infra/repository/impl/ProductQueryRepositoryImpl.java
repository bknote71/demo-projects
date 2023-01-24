package com.bknote71.muzinsa.infra.repository.impl;

import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.dto.condition.ProductSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bknote71.muzinsa.domain.product.QProduct.*;


@RequiredArgsConstructor
@Repository
public class ProductQueryRepositoryImpl {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Product> findAll() {
        return queryFactory
                .selectFrom(product)
                .fetch();
    }

    public Page<Product> findAll(Pageable pageable) {
        // 쿼리 조건이 단순한 경우에는 fetchResults로 카운트 쿼리를 실행해도 괜찮을 듯 하다.
        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = results.getResults();
        long total = results.getTotal();
        // pageable 넘기는 이유: 단순한 페이지 정보를 담고있다. = (page 사이즈(limit), offset, sort)
        // 만약 offset, limit 을 쿼리에 적용하지 않으면 실제로는 전체 쿼리가 나간다.
        // 하지만 PageImpl 에서 pageable 을 넘기기 때문에 page에 대한 정보(limit, offset, sort)는 pageable 것을 사용한다.
        return new PageImpl<>(content, pageable, total);
    }

    public List<Product> findBySearchCondition(ProductSearchCondition condition) {
        return queryFactory
                .selectFrom(product)
                .where(
                        containName(condition.getName()),
                        priceBetween(condition.getPriceGoe(), condition.getPriceLoe()),
                        brandIdEq(condition.getBrandId()))
                .fetch();
    }

    public Page<Product> findBySearchCondition(ProductSearchCondition condition, Pageable pageable) {
        // 굳이 조인같은 복잡한 쿼리가 아닌 이상은 fetchResults 로 카운트 쿼리를 한방에 날려도 상관 없을 듯 하다.
        QueryResults<Product> query = queryFactory
                .selectFrom(product)
                .where(
                        containName(condition.getName()),
                        priceBetween(condition.getPriceGoe(), condition.getPriceLoe()),
                        brandIdEq(condition.getBrandId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = query.getResults();
        long total = query.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression containName(String name) {
        return name != null ? product.name.contains(name) : null;
    }

    private BooleanExpression priceBetween(Integer priceGoe, Integer priceLoe) {
        // Either from or to needs to be non-null;
        // 한쪽은 null 일수는 있다. 이 때 그 한쪽의 범위는 무한대
        // ex) (100, null : 100 ~ 무한), (null ~ 100 : -무한 ~ 100)
        priceGoe = priceGoe == null && priceLoe == null ? 0 : priceGoe;
        return product.price.between(priceGoe, priceLoe);
    }

    private Predicate brandIdEq(Long brandId) {
        // eq(null) is not allowed
        return brandId != null ? product.brandId.eq(brandId) : null;
    }
}
