package com.bknote71.auction.bid.adapter.out.persistence;

import com.bknote71.auction.bid.application.port.out.FinalizeAuctionPort;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FinalizeAuctionAdapter implements FinalizeAuctionPort {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void finalizeAuction(Long itemId) {
        log.info("finalize auction thread: {}", Thread.currentThread());

        final ItemEntity itemEntity = itemRepository.findById(itemId).get();
        itemEntity.setItemStatus(Item.ItemStatus.End);
    }
}
