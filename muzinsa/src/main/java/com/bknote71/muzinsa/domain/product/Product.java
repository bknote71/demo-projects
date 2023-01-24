package com.bknote71.muzinsa.domain.product;

import com.bknote71.muzinsa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private Long brandId;
    private String name;
    private int price;
    private int stock;
    private String introduction; // 글, 이미지, 동영상 <<

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ID")
    private List<ProductOption> options = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public void verifyIfYouCanBuyThis(int quantity) {
        if (!isOnSale()) {
            throw new IllegalStateException("판매중인 상품이 아닙니다");
        }

        if (!hasStockMoreThanQuantity(quantity)) {
            throw new IllegalArgumentException(quantity + " 개 만큼 사기에는 재고가 부족합니다. 현재 재고 : " + stock);
        }
    }

    public boolean isOnSale() {
        return productStatus == ProductStatus.ON_SALE;
    }

    public boolean hasStockMoreThanQuantity(int quantity) {
        return stock > quantity;
    }

    public void changeNameTo(String newName) {
        this.name = newName;
    }

    public void changePriceTo(int newPrice) {
        this.price = newPrice;
    }

    public void changeStockTo(int newStock) {
        this.stock = newStock;
    }

    public void changeIntroductionTo(String newIntro) {
        this.introduction = newIntro;
    }

    public void changeStatusTo(ProductStatus status) {
        this.productStatus = status;
    }

    public void changeOptionList(List<ProductOption> options) {
        this.options.clear();
        this.options.addAll(options);
    }
}
