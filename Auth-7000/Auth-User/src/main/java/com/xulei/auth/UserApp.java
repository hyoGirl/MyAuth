package com.xulei.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description TODO
 * @Date 2019/9/17 20:49
 * @Version 1.0
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserApp {

    public static void main(String[] args) {

        SpringApplication.run(UserApp.class,args);

    }
}
