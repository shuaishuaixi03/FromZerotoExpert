package com.wcx.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordUtil {
    /**
     * 密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String getPBKDF2(String password, String salt) {
        //将16进制字符串形式的salt转换成byte数组
        byte[] bytes = java.util.Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, 10, 256);
        SecretKeyFactory secretKeyFactory = null;
        try { //PBKDF2WithHmacSHA1  PBKDF2WithHmacSHA256
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        try {
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        //将byte数组转换为16进制的字符串
        return java.util.Base64.getEncoder().encodeToString(hash);
    }

    /**
     * 获取盐值
     * @return
     */
    public static String salt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        // 用Base64编码并转换成字符串
        String s = java.util.Base64.getEncoder().encodeToString(bytes);
        return s;
    }
}
