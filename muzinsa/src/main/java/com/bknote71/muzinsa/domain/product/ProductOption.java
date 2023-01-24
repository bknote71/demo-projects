package com.bknote71.muzinsa.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductOption {

    @Id @GeneratedValue
    @Column(name = "PRODUCT_OPTION_ID")
    private Long id;

    // option 값
    @ElementCollection
    @CollectionTable(name = "PRODUCT_OPTION_VALUES",
            joinColumns = @JoinColumn(name = "PRODUCT_OPTION_ID"))
    @Column(name = "OPTION_VALUE") // 기본 값 타입은 @Column 이름 지정이 필수적 <<
    private List<String> values = new ArrayList<>();

    public ProductOption(List<String> values) {
        this.values = values;
    }

    public void changeOptionValue(List<String> newValues) {
        this.values = newValues;
    }

    public void addOptionValue(String value) {
        if (value == null)
            throw new IllegalArgumentException("null인 option 을 넣을 수 없습니다.");

        values.add(value);
    }

    public void deleteOptionValue(String value) {
        values.remove(value);
    }

    @Override public String toString() {
        return Arrays.toString(values.toArray());
    }


}
