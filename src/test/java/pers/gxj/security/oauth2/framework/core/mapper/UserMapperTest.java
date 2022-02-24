package pers.gxj.security.oauth2.framework.core.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pers.gxj.security.oauth2.BaseTest;
import pers.gxj.security.oauth2.common.enums.UserStatus;
import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;

/**
 * Created by xinjie_guo on 2022/2/15.
 */
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_insert(){
        UserDO user = new UserDO();
        user.setUsername("abc");
        user.setPassword("123");
        user.setStatus(UserStatus.NORMAL.name());
        userMapper.insert(user);
    }

    @Test
    public void test_select(){
        UserDO user = new UserDO();
        user.setUsername("abc");
        user.setPassword("123");
        user.setStatus(UserStatus.NORMAL.name());
        userMapper.selectOne(user);
    }

}