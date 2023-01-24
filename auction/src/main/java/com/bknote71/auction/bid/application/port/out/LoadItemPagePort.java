package com.bknote71.auction.bid.application.port.out;

import com.bknote71.auction.bid.domain.Item;
import org.springframework.data.domain.Page;

public interface LoadItemPagePort {

    Page<Item> loadItemPage(Integer page, Integer offset);

}
