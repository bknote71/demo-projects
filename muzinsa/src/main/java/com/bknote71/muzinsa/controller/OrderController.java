package com.bknote71.muzinsa.controller;

import com.bknote71.muzinsa.domain.account.Account;
import com.bknote71.muzinsa.dto.order.OrderDto;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import com.bknote71.muzinsa.dto.request.OrderPayRequest;
import com.bknote71.muzinsa.secuirty.AccountContext;
import com.bknote71.muzinsa.service.OrderProductCommandService;
import com.bknote71.muzinsa.service.PlaceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final PlaceOrderService orderService;
    private final OrderProductCommandService orderProductCommandService;

    @PostMapping("/put") // 주문하기 == 장바구니 담기
    public OrderProductDto put(OrderProductDto orderProductDto, @AuthenticationPrincipal AccountContext ac) {
        Account account = ac.account();
        Long accountId = account.getId();
        return orderProductCommandService.put(orderProductDto, accountId);
    }

    @PostMapping("/pay")
    public OrderDto orderPay(@RequestBody OrderPayRequest orderPayRequest) {
        orderService.placeOrder(
                orderPayRequest.getDeliveryInfo(),
                orderPayRequest.getOrderProductDtos(),
                orderPayRequest.getPaidAmount());

        return null;
    }
}
