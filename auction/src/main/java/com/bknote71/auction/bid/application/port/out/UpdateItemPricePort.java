package com.bknote71.auction.bid.application.port.out;

import com.bknote71.auction.bid.domain.Item;

public interface UpdateItemPricePort {

    void updateCurrentPrice(Long itemId, Long orderPrice);

}
