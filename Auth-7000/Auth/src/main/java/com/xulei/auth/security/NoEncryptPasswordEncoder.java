package com.xulei.auth.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description TODO
 * @Date 2019/7/28 19:54
 * @Version 1.0
 */

public class NoEncryptPasswordEncoder  implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        //密码对比 密码对 true 反之 false
        //CharSequence 数据库中的密码
        //s 前台传入的密码
        return s.equals((String) charSequence);
    }
}
