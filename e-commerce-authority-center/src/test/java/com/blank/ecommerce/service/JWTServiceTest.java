package com.blank.ecommerce.service;

import com.blank.ecommerce.util.TokenParseUtil;
import com.blank.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class JWTServiceTest {
    @Autowired
    private IJWTService ijwtService;

    @Test
    public void testGenerateAndParseToke() throws  Exception{
        final String token = ijwtService.generateToken("blank", "8e15625d6c158ec48f374efb77bd2714");
        log.info("jwt token: [{}]", token);

        final LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        log.info("parse token: [{}]", loginUserInfo);

    }
}
