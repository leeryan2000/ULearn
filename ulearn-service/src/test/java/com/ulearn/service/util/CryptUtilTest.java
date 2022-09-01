package com.ulearn.service.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.db.DaoTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;

import java.security.KeyPair;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CryptUtilTest {

    @Test
    public void encryptPassword() {
        String password = "test";

        String key = String.valueOf(new Date().getTime() % 100000000);
        // String key = "16619742";
        System.out.println(key);

        DES des = new DES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        String encrypt = des.encryptBase64(password);
        System.out.println(encrypt);

        String decrypt = des.decryptStr(encrypt);
        System.out.println(decrypt);
    }
}