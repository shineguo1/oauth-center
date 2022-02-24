package pers.gxj.security.oauth2.framework.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import pers.gxj.security.oauth2.framework.security.handler.LogoutSuccessHandlerImpl;

import javax.sql.DataSource;


/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 16:15
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 重写这个方法
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * {@link JdbcUserDetailsServiceImpl}
         * 配置用户对象，即UserDetailsManager，这个类继承UserDetailsService
         * 自己实现UserDetailsService这个类，需要重写loadUserByUsername方法，可以实现查数据库的需求
         */
        //基于数据库的userDetailsService
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(PASSWORD_ENCODER);

    }


    /**
     * 重写这个方法
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /**
                 * 登录信息的表单字段需要叫username和password
                 * {@link UsernamePasswordAuthenticationFilter#SPRING_SECURITY_FORM_USERNAME_KEY}
                 * {@link UsernamePasswordAuthenticationFilter#SPRING_SECURITY_FORM_PASSWORD_KEY}
                 */
//                .formLogin()//自定义登录页
//                .defaultSuccessUrl("/test/index").permitAll()//登录后默认跳转路径
                // CSRF禁用，因为不使用session
//                .and()
                .csrf().disable()
                // 认证失败处理
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler) //处理类，response返回失败信息
                // .accessDeniedPage("/403error.html") //跳转页面,前后端分离不这么做
                // 基于token，所以不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()//定义请求权限
                .antMatchers("/login", "/data/*").anonymous() //匿名访问，即不登陆访问。
                .antMatchers(
                        HttpMethod.GET,
                        "/file/getFile",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.bpmn"
                ).permitAll() //登录访问，不登录也能访问，允许获取静态资源
                //有role1，role2中任何一个权限可以请求/foo/query资源，authority用逗号隔开
                .antMatchers("/foo/query").hasAnyAuthority("role1,role2")
                //有ROLE_role1，ROLE_role2中任何一个权限可以请求/foo/query资源，配置authority时要加上前缀ROLE_，具体查看hasAnyRole源码
                .antMatchers("/foo/query").hasAnyRole("role1,role2")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                //oauth2需要打开basic鉴权
                .and()
                .httpBasic();

        //基于数据库记录配置token自动登录，request表单参数叫 "remember-me"
        httpSecurity.rememberMe().tokenRepository(inMemoryPersistentTokenRepository())//token数据源
                .tokenValiditySeconds(60) //有效时长60s
                .userDetailsService(userDetailsService());//用户数据源

        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(webFilter, UsernamePasswordAuthenticationFilter.class);
//        // 添加CORS filter
//        httpSecurity.addFilterBefore(corsFilter, WebFilter.class);
//        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * 配置bean对象，否则会报错：There is no PasswordEncoder mapped for the id “null”
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return PASSWORD_ENCODER;
    }


    @Autowired
    private DataSource dataSource;

    /**
     * 基于数据库记录token数据源配置
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        基于jdbcTemplate配置数据源
//        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);
//        自动建表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 基于内存记录token数据源配置
     */

    @Bean
    public PersistentTokenRepository inMemoryPersistentTokenRepository() {
        InMemoryTokenRepositoryImpl tokenRepository = new InMemoryTokenRepositoryImpl();
        return tokenRepository;
    }

}
