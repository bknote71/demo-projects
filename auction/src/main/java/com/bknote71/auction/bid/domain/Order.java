package com.bknote71.auction.bid.domain;

import lombok.*;
import org.springframework.core.Ordered;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class Order {

    private Long id;
    private Long itemId;

    private Long orderPrice;
    private LocalDateTime orderDate;

    public enum OrderStatus {
        YET, ORDERED, CANCEL, FAIL, SUCCESS
    }

    private OrderStatus orderStatus;

    public Order(
            Long itemId,
            Long orderPrice) {
        this.itemId = itemId;
        this.orderPrice = orderPrice;
    }

    public Order(
            Long itemId,
            Long orderPrice,
            LocalDateTime orderDate) {
        this.id = null;
        this.itemId = itemId;
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
        this.orderStatus = OrderStatus.YET;
    }
}
