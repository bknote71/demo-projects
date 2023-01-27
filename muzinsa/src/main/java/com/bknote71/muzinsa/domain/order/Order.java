package com.bknote71.muzinsa.domain.order;

import com.bknote71.muzinsa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Embedded
    private Orderer orderer;
    @Embedded
    private DeliveryInfo deliveryInfo;
    private int totalPrice; // 오더프로덕트 list의 price * quantity 합한 값
    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(List<OrderProduct> orderProducts, Orderer orderer, DeliveryInfo deliveryInfo, OrderStatus orderStatus) {
        this.orderProducts = orderProducts;
        this.orderer = orderer;
        this.deliveryInfo = deliveryInfo;
        this.orderStatus = orderStatus;
    }

    public int calculateTotalPrice() {
        this.totalPrice = orderProducts.stream()
                .mapToInt(OrderProduct::getOrderPrice)
                .sum();
        return totalPrice;
    }

    public void deliveryTo(DeliveryInfo deliveryInfo) {
        if (deliveryInfo == null) {
            throw new IllegalArgumentException("배송 정보가 null 입니다.");
        }
        this.deliveryInfo = deliveryInfo;
    }

    public int getTotalDeliveryFee() {
        return orderProducts.stream()
                .mapToInt(OrderProduct::getDeliveryfee)
                .sum();
    }

    public void completePayment() {
        orderStatus = OrderStatus.PREPARING;
    }

    public void setOrderPrice(int finalAmount) {
        this.orderPrice = finalAmount;
    }
}
