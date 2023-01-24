package com.bknote71.muzinsa.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private Long orderProductId;
    private Long productId;
    private OrderOptionListDto orderOptionListDto;
    private int price;
    private int quantity;
    private int accumulatedMoney;
    private int deliveryfee;
    private Long couponId;

}
