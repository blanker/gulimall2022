package com.blank.ecommerce;

import com.blank.ecommerce.entity.EcommerceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcommerceOrderDao extends PagingAndSortingRepository<EcommerceOrder, Long> {
    Page<EcommerceOrder> findByUserId(Long userId, Pageable pageable);
}
