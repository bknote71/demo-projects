package com.bknote71.auction.bid.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {
    private String name;
    private String explain;
    private Long startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
