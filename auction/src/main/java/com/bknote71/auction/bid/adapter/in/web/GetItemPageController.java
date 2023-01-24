package com.bknote71.auction.bid.adapter.in.web;

import com.bknote71.auction.bid.application.port.in.GetItemPageQuery;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetItemPageController {

    private final GetItemPageQuery getItemPageQuery;

    @GetMapping("/item")
    public ResponseEntity<Page<Item>> getItemPage(Integer page, Integer size) {
        return ResponseEntity.ok(getItemPageQuery.getItemPage(page, size));
    }
}
