package com.zhuang.security.custom;

/**
 * description: CustomMd5PasswordEncoder
 * date: 2023/3/11 22:21
 * author: Zhuang
 * version: 1.0
 */

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.zhuang.common.utils.MD5;

/**
 * <p>
 * 密码处理
 * </p>
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
