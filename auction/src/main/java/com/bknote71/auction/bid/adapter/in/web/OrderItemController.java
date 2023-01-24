package com.bknote71.auction.bid.adapter.in.web;

import com.bknote71.auction.bid.application.port.in.OrderInfo;
import com.bknote71.auction.bid.application.port.in.OrderItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemUseCase orderItemUseCase;

    @PostMapping(value = "/item/order")
    public String orderItem(@RequestBody OrderInfo orderInfo) {
        orderItemUseCase.placeOrder(orderInfo);

        return "success";
    }
}
