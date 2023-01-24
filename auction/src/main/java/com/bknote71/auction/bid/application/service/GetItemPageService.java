package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.application.port.in.GetItemPageQuery;
import com.bknote71.auction.bid.application.port.out.LoadItemPagePort;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetItemPageService implements GetItemPageQuery {

    private final LoadItemPagePort loadItemPagePort;

    @Override
    public Page<Item> getItemPage(Integer page, Integer size) {
        return loadItemPagePort.loadItemPage(page, size);
    }
}
