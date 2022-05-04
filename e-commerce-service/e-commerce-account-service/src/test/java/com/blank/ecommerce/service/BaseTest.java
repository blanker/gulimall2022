package com.blank.ecommerce.service;

import com.blank.ecommerce.filter.AccessContext;
import com.blank.ecommerce.vo.LoginUserInfo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {
    protected final LoginUserInfo loginUserInfo = new LoginUserInfo(
        1L, "blank"
    );

    @BeforeEach
    public void init(){
        AccessContext.setLoginUserInfo(loginUserInfo);
    }

    @AfterEach
    public void tearDown(){
        AccessContext.clearLoginUserInfo();
    }
}
