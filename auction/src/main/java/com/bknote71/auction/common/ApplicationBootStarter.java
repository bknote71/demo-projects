package com.bknote71.auction.common;

import com.bknote71.auction.bid.adapter.out.persistence.ItemEntity;
import com.bknote71.auction.bid.adapter.out.persistence.ItemRepository;
import com.bknote71.auction.bid.application.port.in.ItemInfo;
import com.bknote71.auction.bid.application.service.RegisterItemService;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class ApplicationBootStarter implements ApplicationRunner {

    private final ItemRepository itemRepository;
    private final RegisterItemService registerItemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final ItemEntity i1 = new ItemEntity(null, "i1", "i1", 10L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);
        final ItemEntity i2 = new ItemEntity(null, "i2", "i2", 10000L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);
        final ItemEntity i3 = new ItemEntity(null, "i3", "i3", 12L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);

//        itemRepository.save(i1);
//        itemRepository.save(i2);
//        itemRepository.save(i3);

        registerItemService.registerItem(new ItemInfo("i1", "i1", 10L, LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.SECONDS)));
        registerItemService.registerItem(new ItemInfo("i2", "i2", 10L, LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.SECONDS)));
        registerItemService.registerItem(new ItemInfo("i3", "i3", 10L, LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.SECONDS)));

    }
}
