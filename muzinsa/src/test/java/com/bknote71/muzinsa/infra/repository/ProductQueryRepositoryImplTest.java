package com.bknote71.muzinsa.infra.repository;

import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.dto.condition.ProductSearchCondition;
import com.bknote71.muzinsa.infra.repository.impl.ProductQueryRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
class ProductQueryRepositoryImplTest {

    @Autowired ProductQueryRepositoryImpl repository;
    @Autowired EntityManager em;

    // @beforeEach 중요: 이 메서드 이후 실행하려는 메서드에 @Transactional 이 걸려 있으면 이 메서드도 같은 @Transactional 이 걸린다.
    // 즉 같은 트랜잭션을 사용하기 때문에 이 beforeEach 가 걸린 메서드가 끝나도 트랜잭션은 커밋되지 않는다.
    @BeforeEach
    public void init() {
        Product p1 = Product.builder().name("p1").price(10000).build();
        Product p2 = Product.builder().name("p2").price(10).build();
        Product p3 = Product.builder().name("p3").price(1000).build();

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        em.flush();
        em.clear();
    }

    @Transactional
    @Test
    void findAll() {
        List<Product> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(3);

        Product p1 = result.get(0);
        Product p2 = result.get(1);
        Product p3 = result.get(2);

        Assertions.assertThat(p1.getName()).isEqualTo("p1");
        Assertions.assertThat(p2.getName()).isEqualTo("p2");
        Assertions.assertThat(p3.getName()).isEqualTo("p3");
    }

    @Transactional
    @Test
    void findAllWithSimplePage1() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Product> page = repository.findAll(pageRequest);

        Assertions.assertThat(page.getTotalPages()).isEqualTo(2); // page 개수
        Assertions.assertThat(page.getTotalElements()).isEqualTo(3); // 총 인자 개수
        Assertions.assertThat(page.getSize()).isEqualTo(2); // page 크기
    }

    @Transactional
    @Test
    void findAllWithSimplePage2() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Product> page = repository.findAll(pageRequest);

        Assertions.assertThat(page.getTotalPages()).isEqualTo(1); // page 개수
        Assertions.assertThat(page.getTotalElements()).isEqualTo(3); // 총 인자 개수
        Assertions.assertThat(page.getSize()).isEqualTo(3); // page 크기
    }

    @Transactional
    @Test
    void findBySearchCondition() {
        ProductSearchCondition cond = new ProductSearchCondition(null, null, null, null, 100000);
        ProductSearchCondition cond2 = new ProductSearchCondition("p", null, null, 0, null);
        ProductSearchCondition cond3 = new ProductSearchCondition("p", null, null, 10000, null);

        List<Product> result = repository.findBySearchCondition(cond);
        List<Product> result2 = repository.findBySearchCondition(cond2);
        List<Product> result3 = repository.findBySearchCondition(cond3);

        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(result2.size()).isEqualTo(3);
        Assertions.assertThat(result3.size()).isEqualTo(1);
    }

    @Transactional
    @Test
    void findBySearchConditionWitPage() {
        PageRequest request1 = PageRequest.of(0, 1);
        PageRequest request2 = PageRequest.of(1, 2);
        PageRequest request3 = PageRequest.of(0, 3);

        ProductSearchCondition cond = new ProductSearchCondition(null, null, null, null, 100000);

        Page<Product> result = repository.findBySearchCondition(cond, request1);
        Page<Product> result2 = repository.findBySearchCondition(cond,request2);
        Page<Product> result3 = repository.findBySearchCondition(cond, request3);

        Assertions.assertThat(result.getContent().size()).isEqualTo(1);
        Assertions.assertThat(result2.getContent().size()).isEqualTo(1);
        Assertions.assertThat(result3.getContent().size()).isEqualTo(3);

        System.out.println(result.getPageable());
        System.out.println(result.nextPageable());
    }
}