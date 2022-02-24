package pers.gxj.security.oauth2.framework.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/12/21 15:16
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()//定义请求权限
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and().requestMatchers()
                //放行OAuth2Demo
//                .antMatchers("/OAuth2Demo/**")
                .and().headers().frameOptions().disable()
                .and().csrf().disable();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //如何在授权端的client配置了resourceId，这里也要有
        resources.resourceId("myResource");
    }
}
