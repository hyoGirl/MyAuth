package com.xulei.auth.auth;

import com.xulei.auth.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description OAuth2认证服务器
 * @Date 2019/7/28 13:55
 * @Version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {



    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Bean
    RedisTokenStore tokenStore(){

        return new RedisTokenStore(redisConnectionFactory);
    }


    @Autowired
    private MyUserDetailService userDetailService;


    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置 oauth_client_details【client_id和client_secret等】信息的认证【检查ClientDetails的合法性】服务
     * 设置 认证信息的来源：数据库 (可选项：数据库和内存,使用内存一般用来作测试)
     * 自动注入：ClientDetailsService的实现类 JdbcClientDetailsService (检查 ClientDetails 对象)
     * 这个方法主要是用于校验注册的第三方客户端的信息，可以存储在数据库中，默认方式是存储在内存中，如下所示，注释掉的代码即为内存中存储的方式
     *
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     */
//    原文：https://blog.csdn.net/xyjcfucdi128/article/details/87163388


    //https://www.cnblogs.com/xingxueliao/p/5911292.html
//    配置客户端详情信息（Client Details)：
//    ClientDetailsServiceConfigurer (AuthorizationServerConfigurer 的一个回调配置项，见上的概述) 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService），有几个重要的属性如下列表：
//    clientId：（必须的）用来标识客户的Id。
//    secret：（需要值得信任的客户端）客户端安全码，如果有的话。
//    scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
//    authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
//    authorities：此客户端可以使用的权限（基于Spring Security authorities）。
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
        clients.inMemory() // 使用in-memory存储
                .withClient("android") // client_id
                .scopes("read")
                .secret("android")  // client_secret   android
                .authorizedGrantTypes("password", "authorization_code", "refresh_token") // 该client允许的授权类型
                .and()
                .withClient("webapp") // client_id
                .scopes("read")
                //.secret("webapp")  // client_secret
                .authorizedGrantTypes("implicit")// 该client允许的授权类型
                .and()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .scopes("read");

    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);

        //如果啊，你设置了这个属性的话，那说明你有一个自己的 UserDetailsService 接口的实现，
        // 或者你可以把这个东西设置到全局域上面去（例如 GlobalAuthenticationManagerConfigurer 这个配置对象），
        // 当你设置了这个之后，那么 "refresh_token" 即刷新令牌授权类型模式的流程中就会包含一个检查，用来确保这个账号是否仍然有效，
        // 假如说你禁用了这个账户的话。
        //认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象。


        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager);
        endpoints.tokenServices(defaultTokenServices());
    }


    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * 自定义的token
     * 认证的token是存到redis里的
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());

        tokenServices.setSupportRefreshToken(true);

        //tokenServices.setClientDetailsService(clientDetails());

        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60*60*12);

        // refresh_token默认30天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);

        return tokenServices;
    }

}
