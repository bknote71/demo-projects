package com.bknote71.auction.bid.application.port.out;

import com.bknote71.auction.bid.domain.Order;

public interface RegisterOrderPort {
    void saveOrder(Order order);
}
