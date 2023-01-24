package com.bknote71.auction.bid.application.port.out;

import com.bknote71.auction.bid.domain.Item;

public interface ValidateItemPort {
    void validate(Item item);
}
