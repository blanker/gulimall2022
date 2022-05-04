package com.blank.ecommerce.service.impl;

import cn.hutool.core.codec.Base64Decoder;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.AuthorityConstant;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.dao.EcommerceUserDao;
import com.blank.ecommerce.entity.EcommerceUser;
import com.blank.ecommerce.service.IJWTService;
import com.blank.ecommerce.vo.LoginUserInfo;
import com.blank.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements IJWTService {
    @Autowired
    private EcommerceUserDao ecommerceUserDao;
    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expireDays) throws Exception {
        final EcommerceUser user = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (null == user) {
            log.info("cannot find user: [{}, {}]", username, password);
            return null;
        }
        LoginUserInfo loginUserInfo = new LoginUserInfo(user.getId(), username);
        if (expireDays <= 0){
            expireDays = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }
        ZonedDateTime zdt = LocalDate.now().plus(expireDays, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // payload
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // 过期时间
                .setExpiration(expireDate)
                // 签名
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {
        final EcommerceUser oldUser = ecommerceUserDao.findByUsername(usernameAndPassword.getUsername());
        if (null != oldUser) {
            log.error("username exists: [{}]", usernameAndPassword.getUsername());
            return null;
        }

        EcommerceUser user = new EcommerceUser();
        user.setUsername(usernameAndPassword.getUsername());
        user.setPassword(usernameAndPassword.getPassword());
        user.setExtraInfo("{}");

        user = ecommerceUserDao.save(user);
        log.info("register user success: [{}, {}]", usernameAndPassword.getUsername(), user.getId());

        return generateToken(usernameAndPassword.getUsername(), usernameAndPassword.getPassword());
    }

    private PrivateKey getPrivateKey() throws Exception{
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEy)
        );
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
