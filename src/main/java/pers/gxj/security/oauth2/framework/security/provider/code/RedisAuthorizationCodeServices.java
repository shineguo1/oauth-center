package pers.gxj.security.oauth2.framework.security.provider.code;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;
import pers.gxj.security.oauth2.framework.cache.Cache;

import javax.annotation.Resource;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/12/15 11:11
 */
@Component
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Resource(name="redisCache")
    private Cache<Object> redisCache;

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        //save `prefix_key:code` and `authentication` into redis
        redisCache.put(authCodeKey(code), authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        //get and remove authentication from redis by code
        //finally return the authentication
        OAuth2Authentication ret = (OAuth2Authentication) redisCache.get(authCodeKey(code));
        redisCache.remove(authCodeKey(code));
        return ret;
    }

    private String authCodeKey(String code) {
        return "OAuthCenter:grantCode:" + code;

    }

}
