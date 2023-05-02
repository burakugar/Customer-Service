package com.quotation.repository;

import com.quotation.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findById(final Long id);

    void deleteById(final Long id);

}
