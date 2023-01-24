package com.bknote71.muzinsa.dto.product;

import com.bknote71.muzinsa.domain.product.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.core.KafkaTemplate;

import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ProductOptionDto implements Serializable {
    private List<String> options;

    public ProductOptionDto(List<String> options) {
        this.options = new ArrayList<>(options);
    }

    public ProductOption toProductOption() {
        return new ProductOption(options);
    }
}
