package pers.gxj.security.oauth2.framework.security.provider.clientdetails;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.gxj.security.oauth2.BaseTest;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientDO;
import pers.gxj.security.oauth2.framework.core.mapper.ClientMapper;
import pers.gxj.security.oauth2.framework.security.config.SecurityConfig;

/**
 * Created by xinjie_guo on 2022/2/21.
 */
public class JdbcClientDetailsServiceImplTest extends BaseTest{

    @Autowired
    private ClientMapper clientMapper;

    private static final PasswordEncoder passwordEncoder = SecurityConfig.PASSWORD_ENCODER;

    @Test
    public void insert() {
        ClientDO clientDO = new ClientDO();
        clientDO.setClientId("myClient");
        //appSecret
        clientDO.setClientSecret(passwordEncoder.encode("123456"));
                //授权码、密码、刷新token、简化、客户端
        clientDO.setAuthorizedGrantTypes("authorization_code,password,refresh_token,implicit,client_credentials");
                //作用域
        clientDO.setScope("all,account,pay");
                //资源ID
        clientDO.setResourceIds("myResource");
                //回调地址
        clientDO.setWebServerRedirectUri("http://www.baidu.com,http://www.sina.com/aabc/def?num=1");
        clientMapper.insert(clientDO);
    }

    @Test
    public void select() {
        ClientDO clientDO = new ClientDO();
        clientDO.setClientId("myClient");
        clientMapper.selectByClientId("myClient");
    }

}