package com.blank.ecommerce.dao;

import com.blank.ecommerce.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcommerceAddressDao
        extends JpaRepository<EcommerceAddress, Long> {
    List<EcommerceAddress> findAllByUserId(Long userId);
}
