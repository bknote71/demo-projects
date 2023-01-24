package com.bknote71.muzinsa.dto.request;

import com.bknote71.muzinsa.domain.order.DeliveryInfo;
import com.bknote71.muzinsa.dto.order.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayRequest {
    private DeliveryInfo deliveryInfo;
    private List<OrderProductDto> orderProductDtos;
    private int paidAmount;
}
