package com.bknote71.muzinsa.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "ORDER_PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    private Long productId;
    private Long couponId;
    private int price;
    private int quantity;
    private int deliveryfee;

    // select option
    @ElementCollection
    @CollectionTable(name = "ORDER_OPTION",
            joinColumns = @JoinColumn(name = "ORDER_PRODUCT_ID"))
    private List<OrderOption> option = new ArrayList<>();

    public OrderProduct(Long productId, Long couponId, int price, int quantity, int deliveryfee, List<OrderOption> orderOptions) {
        this.productId = productId;
        this.couponId = couponId;
        this.price = price;
        this.quantity = quantity;
        this.deliveryfee = deliveryfee;
        this.option = orderOptions;
    }

    public int getOrderPrice() {
        return price * quantity;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
