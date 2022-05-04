package com.blank.ecommerce.dao;

import com.blank.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcommerceUserDao extends JpaRepository<EcommerceUser, Long> {
    EcommerceUser findByUsername(String username);

    EcommerceUser findByUsernameAndPassword(String username, String password);
}
