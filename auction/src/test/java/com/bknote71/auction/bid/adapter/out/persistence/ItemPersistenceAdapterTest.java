package com.bknote71.auction.bid.adapter.out.persistence;

import com.bknote71.auction.bid.domain.Item;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemPersistenceAdapterTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    void test() {

        final ABC abc1 = new ABC("1", "1", "1");
        final ABC abc2 = new ABC("2", "2", "2");
        final ABC abc3 = new ABC("3", "3", "3");

        List<ABC> list = new ArrayList<>();

        list.add(abc1);
        list.add(abc2);
        list.add(abc3);

        final List<ABCD> map = modelMapper.map(list, new TypeToken<List<ABCD>>(){}.getType());

        System.out.println(map);
    }

    static class ABC {

        String a;
        String b;
        String c;

        public ABC(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }
    }


    static class ABCD {

        String a;
        String b;
        String c;
        String d;

        public String getA() {
            return a;
        }

        public String getB() {
            return b;
        }

        public String getC() {
            return c;
        }

        public String getD() {
            return d;
        }

        public void setA(String a) {
            this.a = a;
        }

        public void setB(String b) {
            this.b = b;
        }

        public void setC(String c) {
            this.c = c;
        }

        public void setD(String d) {
            this.d = d;
        }

        @Override
        public String toString() {
            return String.format(a + " " + b + " " + c + " " + d);
        }
    }

}