package com.xulei.auth.security;

import com.xulei.auth.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description ResourceServerConfig 是比SecurityConfig 的优先级低的  针对的是除基本授权之外的
 * @Date 2019/7/28 19:45
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    ResourceServerConfig 和 SecurityConfig 之间的关系
//
//    ResourceServerConfig 用于保护 oauth 相关的 endpoints，同时主要作用于用户的登录(form login,Basic auth)
//    SecurityConfig 用于保护 oauth 要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
//
//    所以我们让 SecurityConfig 优先于 ResourceServerConfig，
//
//    且在 SecurityConfig 不拦截 oauth 要开放的资源，
//    在 ResourceServerConfig 中配置需要 token 验证的资源，也就是我们对外提供的接口。所以这里对于所有微服务的接口定义有一个要求，就是全部以 /api 开头。
//
//    如果这里不这样配置的话，在你拿到 access_token 去请求各个接口时会报 invalid_token的提示。
//            ---------------------
//    作者：帅气Dee海绵宝宝
//    来源：CSDN
//    原文：https://blog.csdn.net/xyjcfucdi128/article/details/87163388
//    版权声明：本文为博主原创文章，转载请附上博文链接！


    @Autowired
    private MyUserDetailService userDetailService;



    @Bean
    public PasswordEncoder passwordEncoder() {
        //采用了加密的密码验证
//        return new BCryptPasswordEncoder();
        //采用了不加密的密码验证
        return new NoEncryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf()
                .disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    /**
     * 不定义没有password grant_type,密码模式需要AuthenticationManager支持
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}
