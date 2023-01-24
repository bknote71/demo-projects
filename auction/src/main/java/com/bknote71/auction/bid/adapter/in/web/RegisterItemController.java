package com.bknote71.auction.bid.adapter.in.web;

import com.bknote71.auction.bid.application.port.in.ItemInfo;
import com.bknote71.auction.bid.application.port.in.RegisterItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterItemController {

    private final RegisterItemUseCase registerItemUseCase;

    @PostMapping("/item/register")
    public String registerItem(@RequestBody ItemInfo itemInfo) {
        registerItemUseCase.registerItem(itemInfo);

        return "success";
    }
}
