package com.bknote71.auction.bid.application.port.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// self validating
@Data
@NoArgsConstructor
public class OrderInfo {
    private Long itemId;
    private Long orderPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderDate;

    public OrderInfo(Long itemId, Long orderPrice) {
        this.itemId = itemId;
        this.orderPrice = orderPrice;
    }
    public OrderInfo(Long itemId, Long orderPrice, LocalDateTime orderDate) {
        this.itemId = itemId;
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;


    }
}
