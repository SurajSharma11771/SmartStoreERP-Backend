package com.smartstore.erp.sale.repository;

import com.smartstore.erp.sale.entity.SalesReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReturnRepository extends JpaRepository<SalesReturn, Long> {

    List<SalesReturn> findBySaleId(Long saleId);

    List<SalesReturn> findBySaleIdAndProductId(Long saleId, Long productId);

}