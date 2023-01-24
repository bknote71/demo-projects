package com.bknote71.auction.bid.adapter.out.persistence;

import com.bknote71.auction.bid.application.port.out.LoadItemPagePort;
import com.bknote71.auction.bid.application.port.out.RegisterItemPort;
import com.bknote71.auction.bid.application.port.out.UpdateItemPricePort;
import com.bknote71.auction.bid.application.port.out.ValidateItemPort;
import com.bknote71.auction.bid.domain.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements UpdateItemPricePort, RegisterItemPort, LoadItemPagePort, ValidateItemPort {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Override
    public void updateCurrentPrice(Long itemId, Long orderPrice) {
        final ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        item.setCurrentPrice(orderPrice);
    }

    @Override
    public Long register(Item item) {
        final ItemEntity itemEntity = modelMapper.map(item, ItemEntity.class);
        itemRepository.save(itemEntity);
        return itemEntity.getId();
    }

    @Override
    public Page<Item> loadItemPage(Integer page, Integer size) {
        final Page<ItemEntity> itemEntityPage = itemRepository.findAll(PageRequest.of(page, size));

        Page<Item> itemPage = modelMapper.map(itemEntityPage, new TypeToken<Page<Item>>(){}.getType());

        return itemPage;
    }

    @Override
    public void validate(Item item) {

    }
}
