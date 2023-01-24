package com.bknote71.auction.common;

import com.bknote71.auction.bid.application.port.in.OrderInfo;
import com.bknote71.auction.bid.domain.Order;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.Module;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

import java.time.LocalDateTime;

public class ModelMapperTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void mappingTest() {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        final OrderInfo orderInfo = new OrderInfo(1L, 11L);
        final Order order = modelMapper.map(orderInfo, Order.class);

        System.out.println(order);

        Assertions.assertThat(order.getItemId()).isEqualTo(1L);
        Assertions.assertThat(order.getOrderPrice()).isEqualTo(11L);
    }

    @Test
    void localdatetime() {
        final ABC abc = new ABC();
        abc.localDateTime = LocalDateTime.now();
        final LocalDateTime of = LocalDateTime.of(2022, 10, 29, 10, 10, 10);
        System.out.println(of);
        System.out.println(abc.localDateTime);
        abc.localDateTime = of;
        final ABCD map = modelMapper.map(abc, ABCD.class);
        System.out.println(map.localDateTime);
    }


    static class ABC {
        LocalDateTime localDateTime;

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }
    }
    static class ABCD {
        LocalDateTime localDateTime;

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }
    }
}
