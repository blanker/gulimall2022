package com.blank.ecommerce.service;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Slf4j
public class RSATest {
    @Test
    public void generateKeyBytes() throws  Exception{
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        final KeyPair keyPair = keyPairGenerator.genKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        log.info("private key: [{}]", Base64.encode(rsaPrivateKey.getEncoded()));
        log.info("public key: [{}]", Base64.encode(rsaPublicKey.getEncoded()));
    }
}
