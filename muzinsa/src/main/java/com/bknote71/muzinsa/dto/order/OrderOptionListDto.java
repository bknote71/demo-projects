package com.bknote71.muzinsa.dto.order;

import com.bknote71.muzinsa.domain.order.OrderOption;
import com.bknote71.muzinsa.domain.product.ProductOption;
import com.bknote71.muzinsa.dto.product.ProductOptionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
public class OrderOptionListDto {
    private List<OrderOptionDto> optionDtos;

    public OrderOptionListDto(OrderOptionDto... orderOptionDtos) {
        optionDtos = Arrays.asList(orderOptionDtos);
    }

    public OrderOptionListDto(List<OrderOption> options) {
        optionDtos = new ArrayList<>();
        for (OrderOption option : options) {
            optionDtos.add(new OrderOptionDto(option.getValue()));
        }
    }

    public List<OrderOption> toOrderOptionList() {
        List<OrderOption> orderOptions = new ArrayList<>();
        for (OrderOptionDto orderOptionDto : optionDtos) {
            orderOptions.add(orderOptionDto.toOrderOption());
        }
        return orderOptions;
    }
}
