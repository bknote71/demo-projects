package com.bknote71.auction.bid.adapter.out.persistence;

import com.bknote71.auction.bid.application.port.out.RegisterOrderPort;
import com.bknote71.auction.bid.application.port.out.ValidateOrderPort;
import com.bknote71.auction.bid.domain.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements ValidateOrderPort, RegisterOrderPort {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Override
    public void validate(Order order) {

        // select for update
        final ItemEntity item = itemRepository.findById(order.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 1. 주문 입찰 시간 확인: order.orderDate < item.endDate
        if (!order.getOrderDate().isBefore(item.getEndDate())) {
            throw new IllegalArgumentException("입찰 가능한 시간이 지났습니다.");
        }

        // 2. 주문 최소 금액 이상인지 확인하기
        if (order.getOrderPrice() < item.getStartPrice()) {
            throw new IllegalArgumentException("최소 입찰 금액 이상이어야 합니다.");
        }

        // 3. 본인의 입찰 금액 >= 현재가인지 검증하기
        if (item.getCurrentPrice() != null && order.getOrderPrice() <= item.getCurrentPrice()) {
            throw new IllegalArgumentException("입찰 금액이 현재가보다 작아 입찰하실 수 없습니다.");
        }
    }

    @Override
    public void saveOrder(Order order) {
        final OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderRepository.save(orderEntity);
    }
}
