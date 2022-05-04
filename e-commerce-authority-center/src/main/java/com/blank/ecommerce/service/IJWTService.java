package com.blank.ecommerce.service;

import com.blank.ecommerce.vo.UsernameAndPassword;

public interface IJWTService {
    String generateToken(String username, String password) throws Exception;
    String generateToken(String username, String password, int expireDays) throws Exception;
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
