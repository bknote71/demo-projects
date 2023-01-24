package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.application.port.out.FinalizeAuctionPort;
import com.bknote71.auction.bid.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ItemPostProcessHandler {

    private final FinalizeAuctionPort finalizeAuctionPort;
    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public ItemPostProcessHandler(FinalizeAuctionPort finalizeAuctionPort) {
        this.finalizeAuctionPort = finalizeAuctionPort;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }

    void process(Item item) {
        final Duration duration = Duration.between(item.getStartDate(), item.getEndDate());
        System.out.println("duration seconds: " + duration.getSeconds());
        scheduledExecutorService.schedule(new ItemPostProcessTask(finalizeAuctionPort, item.getId()), duration.getSeconds(), TimeUnit.SECONDS);
    }

    static class ItemPostProcessTask implements Runnable {

        private FinalizeAuctionPort finalizeAuctionPort;
        private Long itemId;

        public ItemPostProcessTask(FinalizeAuctionPort finalizeAuctionPort, Long itemId) {
            this.finalizeAuctionPort = finalizeAuctionPort;
            this.itemId = itemId;
        }

        @Override
        public void run() {
            log.info("item post process task run()");
            System.out.println("hellooooooooooo " + itemId);
            finalizeAuctionPort.finalizeAuction(itemId);
            log.info("end of run");
        }
    }

}
