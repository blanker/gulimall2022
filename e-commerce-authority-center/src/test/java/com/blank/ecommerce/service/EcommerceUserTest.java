package com.blank.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import com.blank.ecommerce.dao.EcommerceUserDao;
import com.blank.ecommerce.entity.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class EcommerceUserTest {
    @Autowired
    private EcommerceUserDao ecommerceUserDao;

    @Test
    public void createNewRecord(){
        EcommerceUser user = new EcommerceUser();
        user.setUsername("blank");
        user.setPassword(MD5.create().digestHex("blank"));
        user.setExtraInfo("extra info");
        ecommerceUserDao.save(user);
    }

    @Test
    public void testFindByUsername(){
        final EcommerceUser blank = ecommerceUserDao.findByUsername("blank");
        log.info("record: [{}]", blank);
    }
}
