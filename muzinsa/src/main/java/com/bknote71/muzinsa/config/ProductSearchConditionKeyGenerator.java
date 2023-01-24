package com.bknote71.muzinsa.config;

import com.bknote71.muzinsa.dto.condition.ProductSearchCondition;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ProductSearchConditionKeyGenerator implements KeyGenerator {
    @Override public Object generate(Object target, Method method, Object... params) {
        ProductSearchCondition cond = (ProductSearchCondition) params[0];
        return String.format("searchCond: %s %d %d %d", cond.getName(), cond.getBrandId(), cond.getPriceGoe(), cond.getPriceLoe());
    }
}
