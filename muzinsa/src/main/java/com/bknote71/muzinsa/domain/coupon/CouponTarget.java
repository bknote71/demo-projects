package com.bknote71.muzinsa.domain.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CouponTarget {
    @Enumerated(EnumType.STRING)
    private TargetType targetType; // product or brand

    // brandIds or productIds 하나만 들어감
    @ElementCollection
    @CollectionTable(name = "TARGET_IDS",
            joinColumns = @JoinColumn(name = "COUPON_ID"))
    private List<Long> targetIds = new ArrayList<>();

    public CouponTarget(String targetType, List<Long> ids) {
        this.targetType = TargetType.valueOf(targetType.toUpperCase());
        targetIds = new ArrayList<>(ids);
    }

    public boolean isApplicable(Long brandId, Long productId) {
        if (targetType == TargetType.ALL) {
            return true;
        }
        Long targetId = targetType == TargetType.BRAND ? brandId : productId;
        for (Long id : targetIds) {
            if (id == targetId) return true;
        }
        return false;
    }
}
