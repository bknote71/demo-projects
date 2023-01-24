package com.bknote71.muzinsa.dto.order;

import com.bknote71.muzinsa.domain.order.OrderOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOptionDto {
    private String value;

    public OrderOption toOrderOption() {
        return new OrderOption(value);
    }
}
