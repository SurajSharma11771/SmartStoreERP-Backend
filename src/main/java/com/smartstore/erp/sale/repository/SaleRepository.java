package com.smartstore.erp.sale.repository;

import com.smartstore.erp.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Transactional
void deleteById(Long id);
}