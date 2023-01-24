package com.bknote71.muzinsa.dto.product;

import com.bknote71.muzinsa.domain.product.Product;
import com.bknote71.muzinsa.domain.product.ProductOption;
import com.bknote71.muzinsa.domain.product.ProductStatus;
import com.bknote71.muzinsa.dto.order.OrderOptionListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long productId;
    private Long brandId;
    private String name;
    private Integer price;
    private Integer stock;
    private String introduction; // 글, 이미지, 동영상 <<
    private String status;
    private ProductOptionListDto productOptionListDto;

    public ProductDto(Product entity) {
        this.productId = entity.getId();
        this.brandId = entity.getBrandId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.stock = entity.getStock();
        this.introduction = entity.getIntroduction();
        this.status = entity.getProductStatus().toString();
        this.productOptionListDto = new ProductOptionListDto(entity.getOptions());
    }
}
