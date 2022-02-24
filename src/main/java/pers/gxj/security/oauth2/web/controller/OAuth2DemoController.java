package pers.gxj.security.oauth2.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.gxj.security.oauth2.framework.security.config.AuthorizationConfig;

import java.security.Principal;
import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/12/21 11:20
 */
@RestController
@RequestMapping("/OAuth2Demo")
@Slf4j
public class OAuth2DemoController {

//    @Autowired
    TokenStore tokenStore;

//    @Autowired
    TokenService tokenService;

    @Autowired
    AuthorizationConfig authConfig;


    @PostMapping("/oauthTest/account")
    @PreAuthorize("#oauth2.hasScope('account')")
    public Object oauthTest(@RequestBody Map param) {
        System.out.println("oauthTest PARAM:" + param);
        System.out.println(param);
        return param;

        /*
         直接访问，失败信息：
         {
            "timestamp": "2021-12-21T06:21:04.040+0000",
            "status": 403,
            "error": "Forbidden",
            "message": "Forbidden",
            "path": "/OAuth2Demo/oauthTest"
         }
         */
    }

    @PostMapping("/oauthTest/pay")
    @PreAuthorize("#oauth2.hasScope('pay')")
    public Object oauthTest2(@RequestBody Map param) {
        System.out.println("oauthTest PARAM:" + param);
        System.out.println(param);
        return param;
    }

    @PostMapping("/oauthTest")
    public Object oauthTest3(@RequestBody Map param) {
        System.out.println("oauthTest PARAM:" + param);
        System.out.println(param);
        return param;
    }

    @PostMapping("/tokenCheck")
    public Object tokenCheck(Principal principal, @RequestParam("accessToken") String token) {
        System.out.println("accessToken PARAM:" + token);
        System.out.println(token);
        System.out.println("tokenStore内token信息:" + JSON.toJSONString(authConfig.getEndPoints().getTokenStore().readAccessToken(token)));
        System.out.println("token认证状态下，oauth2Request信息:" + (principal instanceof OAuth2Authentication ? ((OAuth2Authentication)principal).getOAuth2Request() : null));
        System.out.println("oauth2Request用户登录信息:" + JSON.toJSONString(authConfig.getEndPoints().getTokenStore().readAuthentication(token).getUserAuthentication()));
        System.out.println("oauth2Request授权信息:" + JSON.toJSONString(authConfig.getEndPoints().getTokenStore().readAuthentication(token).getOAuth2Request()));
        return principal;
    }
}
