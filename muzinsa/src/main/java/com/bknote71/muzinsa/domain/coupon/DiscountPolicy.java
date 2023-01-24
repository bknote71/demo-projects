package com.bknote71.muzinsa.domain.coupon;


import java.util.function.BiFunction;

public enum DiscountPolicy {

    AMOUNT((origin, discount) -> discount),
    PERCENT((origin, discount) -> (int) ((double) origin * discount / 100.0)), // 10% 할인: 1000 원 * 0.1 = 100 won 할인
    NONE((origin, discount) -> 0);


    private BiFunction<Integer, Integer, Integer> function;

    DiscountPolicy(BiFunction<Integer, Integer, Integer> function) {
        this.function = function;
    }

    public int calculateDiscountAmount(int origin, int discount) {
        return function.apply(origin, discount);
    }
}

