package com.bknote71.auction.bid.adapter.out.persistence;

import com.bknote71.auction.bid.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;
    private Long orderPrice;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Order.OrderStatus orderStatus;

}
