package com.ulearn.service.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: Ryan
 * @Description:
 * @Date: 2022/9/1 2:46
 */

public class CryptUtil {

    public static String encrypt(String key, String data) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        return aes.encryptBase64(data);
    }

    public static String decrypt(String key, String data) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        return aes.decryptStr(data);
    }
}
