package pers.gxj.security.oauth2.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.gxj.security.oauth2.common.exception.BizException;
import pers.gxj.security.oauth2.framework.core.entity.req.CheckScopeReq;
import pers.gxj.security.oauth2.framework.core.service.ScopeService;
import pers.gxj.security.oauth2.framework.security.config.AuthorizationConfig;

import java.security.Principal;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 10:02
 */
@RestController
@RequestMapping("/oauth/scope")
@Slf4j
public class ScopeController {

    @Autowired
    private ScopeService scopeService;

    @GetMapping
    public Object getScope(Principal principal) {
        if (!(principal instanceof OAuth2Authentication)) {
            throw new BizException("非OAuth2授权登录");
        }
        OAuth2Authentication oauth2Auth = (OAuth2Authentication) principal;
        return oauth2Auth.getOAuth2Request().getScope();
    }

    @PostMapping("/check")
    public Object check(Principal principal, @RequestBody CheckScopeReq req) {
        if (!(principal instanceof OAuth2Authentication)) {
            throw new BizException("非OAuth2授权登录");
        }
        OAuth2Authentication oauth2Auth = (OAuth2Authentication) principal;

        return scopeService.check(oauth2Auth, req);
    }


}
