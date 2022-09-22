package com.wcx.utils;

import com.wcx.common.UserException;
import com.wcx.enums.UserExceptionEnum;

public class ValidUtil {

    /** 判断用户账号是否符合要求 */
    public static boolean isVaildAccount(String account) {
        // 账号为空，抛出异常
        if (account == null) {
            throw new UserException(UserExceptionEnum.ACCOUNT_IS_NULL);
        }
        // 账号长度不符合，返回false
        if (account.length() < 6 || account.length() > 16) {
            return false;
        }
        // 保护除数字、字母外的特殊字符，返回false
        for (char c : account.toCharArray()) {
            if(!isNumber(c) && !isCharacter(c)) {
                return false;
            }
        }
        return true;
    }
    /** 判断用户昵称是否符合要求 */
    public static boolean isVaildName(String name) {
        // 昵称为空，抛出异常
        if (name == null) {
            throw new UserException(UserExceptionEnum.NAME_IS_NULL);
        }
        // 昵称长度不符合，返回false
        if (name.length() < 2 || name.length() > 16) {
            return false;
        }
        String[] words = {"尼玛", "sb", "草泥马"};
        Trie trie = new Trie();
        for (int i = 0; i < words.length; i ++) {
            trie.insert(words[i]);
        }
        return true && !trie.startPrefix(name);
    }
    /** 判断用户密码是否符合规范*/
    public static boolean isVaildPassword(String password) {
        if (password == null) {
            throw new UserException(UserExceptionEnum.PASSWORD_IS_NULL);
        }
        // 密码中是否含数字
        boolean hasNum = false;
        // 密码中是否含字母
        boolean hasCharacter = false;
        for (char c : password.toCharArray()) {
            if (isNumber(c)) {
                hasNum = true;
            }
            if (isCharacter(c)) {
                hasCharacter = true;
            }
        }
        return hasNum && hasCharacter;
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
    private static boolean isCharacter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}
