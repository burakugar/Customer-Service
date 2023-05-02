package com.quotation.repository;

import com.quotation.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findById(final Long subscriptionId);
    void deleteById(final Long subscriptionId);

    SubscriptionEntity save(SubscriptionEntity subscription);
}
