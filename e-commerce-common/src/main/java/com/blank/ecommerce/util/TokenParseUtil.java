package com.blank.ecommerce.util;

import cn.hutool.core.codec.Base64Decoder;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.constant.CommonConstant;
import com.blank.ecommerce.vo.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TokenParseUtil {
    public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception{
        if (null == token) {
            return null;
        }
        final Jws<Claims> claimsJws = parseClaimFromToken(token);
        final Claims body = claimsJws.getBody();
        final Date expiration = body.getExpiration();
        if (expiration.before(Calendar.getInstance().getTime())) {
            return null;
        }

        return JSON.parseObject((String) body.get(CommonConstant.JWT_USER_INFO_KEY),
                LoginUserInfo.class);
    }

    private static Jws<Claims> parseClaimFromToken(String token) throws Exception{
        return Jwts
                .parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token);
    }

    private static PublicKey getPublicKey() throws Exception{
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(CommonConstant.PUBLIC_KEY)
        );
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
