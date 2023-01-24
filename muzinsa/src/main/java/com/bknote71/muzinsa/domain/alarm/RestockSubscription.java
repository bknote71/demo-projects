package com.bknote71.muzinsa.domain.alarm;

import com.bknote71.muzinsa.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestockSubscription extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "WAREHOUSING_ID")
    private Long id;

    private Long productId;
    private Long accountId;
    private String email; // 이메일 방식
    private String cellphone; // 카카오톡 방식

    private boolean processed; // 알람 처리 했는지 여부 - 기본 false
}
