package com.blank.ecommerce.dao;

import com.blank.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcommerceBalanceDao
        extends JpaRepository<EcommerceBalance, Long> {
    EcommerceBalance findByUserId(Long userId);
}
