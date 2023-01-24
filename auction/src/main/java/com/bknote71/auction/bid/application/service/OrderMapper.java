package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.application.port.in.OrderInfo;
import com.bknote71.auction.bid.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order mapFrom(OrderInfo orderInfo) {
        return new Order(orderInfo.getItemId(), orderInfo.getOrderPrice(), orderInfo.getOrderDate());
    }
}
