package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.application.port.in.OrderInfo;
import com.bknote71.auction.bid.application.port.in.OrderItemUseCase;
import com.bknote71.auction.bid.application.port.out.RegisterOrderPort;
import com.bknote71.auction.bid.application.port.out.UpdateItemPricePort;
import com.bknote71.auction.bid.application.port.out.ValidateOrderPort;
import com.bknote71.auction.bid.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService implements OrderItemUseCase {

    private final ValidateOrderPort validateOrderPort;
    private final UpdateItemPricePort updateItemPricePort;
    private final RegisterOrderPort registerOrderPort;
    private final OrderMapper orderMapper;

    @Override
    public void placeOrder(OrderInfo orderInfo) {
        final Order order = orderMapper.mapFrom(orderInfo);

        // 검증
        validateOrderPort.validate(order);

        // 최종: 저장
        registerOrderPort.saveOrder(order);
        updateItemPricePort.updateCurrentPrice(order.getItemId(), order.getOrderPrice());
    }
}
