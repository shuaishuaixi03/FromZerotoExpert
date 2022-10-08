package com.wcx.utils;

import java.util.Date;
import java.util.Random;

public class SessionIdUtil {
    /**
     * 生成sessionId(随机数+时间+account)
     * @param account
     * @return
     */
    public static String createSessionId(String account) {
        String random = String.valueOf((new Random().nextInt(900000) + 100000));
        return random + System.currentTimeMillis() + account;
    }
}
