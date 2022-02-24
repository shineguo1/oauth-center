package pers.gxj.security.oauth2.framework.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * 授权服务端配置
 *
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 16:15
 */
@Component
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder = SecurityConfig.PASSWORD_ENCODER;

    /**
     * ClientDetailsServiceConfiguration这个配置类也定义了 "ClientDetailsService" Bean,而且是用我们注入的这个bean再次注入的。所以这里不能用autowired注入
     */
    @Resource(name = "jdbcClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    private AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许使用表单参数而不是基本身份验证来验证客户端
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //数据库配置
        clients.withClientDetails(clientDetailsService);
    }

    @Autowired
    private DataSource dataSource;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //授权表单提交页面映射（自定义）
//                .pathMapping("/oauth/confirm_assess","my/confirm_assess")
                ///认证管理器
//                .authenticationManager(authenticationManager)
                //用户信息查询服务
                .userDetailsService(userDetailsService)
                //自定义<授权码,用户认证信息>存储服务，demo提供redis实现思路
                .authorizationCodeServices(authorizationCodeServices)
                //允许TokenEndpoint接受GET请求和POST请求
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(new JdbcTokenStore(dataSource));
        authorizationServerEndpointsConfigurer = endpoints;
    }

    public AuthorizationServerEndpointsConfigurer getEndPoints() {
        return authorizationServerEndpointsConfigurer;
    }
}
