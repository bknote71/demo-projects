package com.bknote71.auction.bid.application.port.in;

import com.bknote71.auction.bid.domain.Item;
import org.springframework.data.domain.Page;

public interface GetItemPageQuery {
    Page<Item> getItemPage(Integer page, Integer size);
}
