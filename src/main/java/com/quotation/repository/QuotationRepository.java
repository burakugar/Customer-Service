package com.quotation.repository;

import com.quotation.entity.QuotationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuotationRepository extends CrudRepository<QuotationEntity, Long> {
    QuotationEntity save(QuotationEntity quotation);

    void deleteById(Long id);

    Optional<QuotationEntity> findById(Long id);

}
