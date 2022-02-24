package pers.gxj.security.oauth2.framework.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * security注解启用配置类
 *
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/12/21 11:33
 */
@Configuration
//启用@PreAuthorize、@PostAuthorize注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        //启用security-oauth2的注解，如：@PreAuthorize("#oauth.hasScope('account')")
        return new OAuth2MethodSecurityExpressionHandler();
    }
}