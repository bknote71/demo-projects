package com.bknote71.muzinsa.domain.order;

public enum OrderStatus {

    // 입금 전, (결제 완료)상품 준비, 출고, 배달 중, 배달 완료, 취소
    PAYMENT_WAITING, PREPARING, SHIPPED, IN_DELIVERY, DELIVERY_COMPLETE, CANCEL
}
