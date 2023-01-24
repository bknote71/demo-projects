package com.bknote71.auction.bid.domain;

import lombok.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String explain;
//    private Long orderCount;

    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public enum ItemStatus {
        Selling, End
    }

    private ItemStatus itemStatus;

    Item(
            Long id, String name, String explain,
            Long startPrice, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, name, explain, startPrice, null, startDate, endDate);
    }

    Item(
            Long id, String name, String explain,
            Long startPrice, Long currentPrice, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.explain = explain;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemStatus = ItemStatus.Selling;
    }

    public static Item withId(
            Long itemId, String name, String explain,
            Long startPrice, LocalDateTime startDate, LocalDateTime endDate) {
        return new Item(itemId, name, explain, startPrice, startDate, endDate);
    }
}

