package pers.gxj.security.oauth2.framework.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pers.gxj.security.oauth2.BaseTest;
import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;
import pers.gxj.security.oauth2.framework.security.provider.userdetails.AuthUser;

import javax.annotation.Resource;

/**
 * Created by xinjie_guo on 2022/2/17.
 */
public class CacheTest extends BaseTest {

    @Resource(name="redisCache")
    private Cache<Object> redisCache;

    @Test
    public void test(){
        UserDO user = new UserDO();
        user.setUsername("xiaoB");
        user.setPassword("123456");
        String s = JSON.toJSONString(new AuthUser(user, Sets.newHashSet(new SimpleGrantedAuthority("aaaaa"))));
        JSON.parseObject(s, AuthUser.class);

        redisCache.put("abc", new AuthUser(user));
        redisCache.get("abc");
    }

}