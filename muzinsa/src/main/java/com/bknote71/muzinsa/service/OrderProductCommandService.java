package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.order.OrderProduct;
import com.bknote71.muzinsa.domain.repository.OrderProductRepository;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderProductCommandService {

    private final OrderProductRepository orderProductRepository;

    public OrderProductDto put(OrderProductDto dto, Long accountId) {
        OrderProduct orderProduct = new OrderProduct(
                dto.getProductId(),
                null,
                dto.getPrice(),
                dto.getQuantity(),
                dto.getDeliveryfee(),
                dto.getOrderOptionListDto().toOrderOptionList());

        OrderProduct newOrderProduct = orderProductRepository.save(orderProduct);
        dto.setOrderProductId(newOrderProduct.getId());
        return dto;
    }

    public void delete(Long orderProductId) {
        orderProductRepository.delete(orderProductId);
    }
}
