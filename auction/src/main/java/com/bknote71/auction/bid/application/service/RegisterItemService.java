package com.bknote71.auction.bid.application.service;

import com.bknote71.auction.bid.application.port.in.ItemInfo;
import com.bknote71.auction.bid.application.port.in.RegisterItemUseCase;
import com.bknote71.auction.bid.application.port.out.RegisterItemPort;
import com.bknote71.auction.bid.application.port.out.ValidateItemPort;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterItemService implements RegisterItemUseCase {

    private final RegisterItemPort registerItemPort;
    private final ValidateItemPort validateItemPort;
    private final ItemPostProcessHandler itemPostProcessHandler;
    private final ModelMapper modelMapper;

    @Override
    public void registerItem(ItemInfo itemInfo) {
        final Item item = modelMapper.map(itemInfo, Item.class);

        // 검증
        validateItemPort.validate(item);

        // 등록
        // 1. db에 저장
        item.setId(registerItemPort.register(item));

        // 2. 아이템을 스케쥴러에 등록
        itemPostProcessHandler.process(item);
    }
}
