package com.bknote71.muzinsa.dto;

import com.atomikos.icatch.jta.UserTransactionImp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    private Long couponId;
    private Long accountId;
    private Long orderProductId;
    // 우선은 타입으로 쿠폰 분류 << 나중에 도형성을 활용
    private String type;
    private int discount;

}
