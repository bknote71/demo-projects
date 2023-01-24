package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.adapter.out.persistence.ItemEntity;
import com.bknote71.auction.bid.adapter.out.persistence.ItemRepository;
import com.bknote71.auction.bid.application.port.out.UpdateItemPricePort;
import com.bknote71.auction.bid.domain.Item;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ItemPostProcessHandlerTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemPostProcessHandler itemPostProcessHandler;

    @Autowired
    UpdateItemPricePort updateItemPricePort;

    @Autowired
    EntityManager entityManager;

    @Test
    void ScheduledExecutorServiceTest() throws InterruptedException {
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.schedule(() -> System.out.println("hello"), 3, TimeUnit.SECONDS);

        System.out.println("before hello");

        Thread.sleep(6000);

        System.out.println("after hello");
    }

    @Test
    @Transactional
    void itemPostProcessTest() throws InterruptedException {

        final ItemEntity i1 = new ItemEntity(4L, "i1", "i1", 10L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);
        final ItemEntity i2 = new ItemEntity(5L, "i2", "i2", 10000L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);
        final ItemEntity i3 = new ItemEntity(6L, "i3", "i3", 12L, null, LocalDateTime.now(), LocalDateTime.now().plus(5, ChronoUnit.SECONDS), Item.ItemStatus.Selling);

        final ModelMapper modelMapper = new ModelMapper();
        final Item item1 = modelMapper.map(i1, Item.class);
        final Item item2 = modelMapper.map(i2, Item.class);
        final Item item3 = modelMapper.map(i3, Item.class);

        System.out.println(item1.getId());
        System.out.println(item2.getId());
        System.out.println(item3.getId());

        itemRepository.save(i1);
        itemRepository.save(i2);
        itemRepository.save(i3);

        itemPostProcessHandler.process(item1);
        itemPostProcessHandler.process(item2);
        itemPostProcessHandler.process(item3);

//        updateItemStatePort.finalizeAuction(4L);
//        updateItemStatePort.finalizeAuction(5L);
//        updateItemStatePort.finalizeAuction(6L);

        System.out.println("before hello");
        Thread.sleep(8000);
        System.out.println("after hello");

        final List<ItemEntity> all = itemRepository.findAll();

        all.forEach(i -> {
            System.out.println(i.getId());
            System.out.println(i.getName());
            System.out.println(i.getExplain());
            System.out.println(i.getStartPrice());
            System.out.println(i.getCurrentPrice());
            System.out.println(i.getStartDate());
            System.out.println(i.getEndDate());
            System.out.println(i.getItemStatus());
        });
    }

}