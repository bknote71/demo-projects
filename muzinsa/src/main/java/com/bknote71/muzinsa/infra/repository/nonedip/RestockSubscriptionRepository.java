package com.bknote71.muzinsa.infra.repository.nonedip;

import com.bknote71.muzinsa.domain.alarm.RestockSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestockSubscriptionRepository extends JpaRepository<RestockSubscription, Long> {

    @Query("select r from RestockSubscription r where r.productId = :productId and r.processed = FALSE")
    List<RestockSubscription> findByProductIdNotYetProcessed(@Param("productId") Long productId);

}
