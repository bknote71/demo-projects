package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.account.Account;
import com.bknote71.muzinsa.domain.alarm.RestockSubscription;
import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.product.ProductStatus;
import com.bknote71.muzinsa.domain.repository.ProductRepository;
import com.bknote71.muzinsa.dto.product.ProductDto;
import com.bknote71.muzinsa.dto.product.ProductOptionListDto;
import com.bknote71.muzinsa.infra.repository.nonedip.AccountRepository;
import com.bknote71.muzinsa.infra.repository.nonedip.RestockSubscriptionRepository;
import com.bknote71.muzinsa.service.alarm.RestockAlarmProducer;
import com.bknote71.muzinsa.service.alarm.RestockAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.bknote71.muzinsa.utils.ProductUtils.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final RestockSubscriptionRepository restockSubscriptionRepository;

    private final RestockAlarmService alarmService;

    @Transactional
    public ProductDto registerProduct(ProductDto dto) {
        Product product = Product.builder()
                .brandId(dto.getBrandId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .introduction(dto.getIntroduction())
                .options(dto.getProductOptionListDto().toProductOptionList())
                .productStatus(ProductStatus.ON_SALE)
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct);
    }

    @Transactional
    public ProductDto changeProduct(ProductDto dto) {
        Product product = checkIfProductExistsAndReturn(dto.getProductId());
        // util로 하는게 좋을까여 아니면 그냥 하는게 좋을까여 ...
        if (hasName(dto)) {
            product.changeNameTo(dto.getName());
            log.info("changed name: {}", product.getName());
        }

        if (hasPrice(dto)) {
            product.changePriceTo(dto.getPrice());
            log.info("changed price: {}", product.getPrice());

        }

        if (dto.getStock() != null) {
            product.changeStockTo(dto.getStock());
            log.info("changed stock: {}", product.getStock());
            alarmService.produce(dto.getProductId().toString());
        }

        if (hasText(dto.getIntroduction())) {
            product.changeIntroductionTo(dto.getIntroduction());
            log.info("changed intro: {}", product.getIntroduction());
        }

        if (dto.getStatus() != null) {
            product.changeStatusTo(ProductStatus.valueOf(dto.getStatus()));
            log.info("changed status: {}", product.getProductStatus());

        }

        if (dto.getProductOptionListDto() != null) {
            changeProductOptions(dto, product);
            log.info("changed options: {}", product.getOptions());
        }

        return new ProductDto(product);
    }


    public Boolean subscribeRestockAlarm(Long productId, Account account) {
        checkIfProductExistsAndReturn(productId);

        RestockSubscription subscription = RestockSubscription.builder()
                .productId(productId)
                .accountId(account.getId())
                .email(account.getEmail())
                .cellphone(account.getCellphone())
                .build();
        RestockSubscription save = restockSubscriptionRepository.save(subscription);

        return save != null;
    }


    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private void changeProductOptions(ProductDto dto, Product product) {
        ProductOptionListDto optionListDto = dto.getProductOptionListDto();
        product.changeOptionList(optionListDto.toProductOptionList());
    }


    private Product checkIfProductExistsAndReturn(Long productId) {
        if (productId == null) {
            throw new NullPointerException("product id is null");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalIdentifierException("id 에 대응하는 엔티티가 없습니다."));
        return product;
    }

    private Account checkIfAccountExistsAndReturn(Long accountId) {
        if (accountId == null) {
            throw new NullPointerException("product id is null");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalIdentifierException("id 에 대응하는 엔티티가 없습니다."));
        return account;
    }
}
