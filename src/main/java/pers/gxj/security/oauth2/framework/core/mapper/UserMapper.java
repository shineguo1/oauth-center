package pers.gxj.security.oauth2.framework.core.mapper;

import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;
import pers.gxj.security.oauth2.framework.mybatis.MyBaseMapper;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 9:30
 */
public interface UserMapper extends MyBaseMapper<UserDO> {

    default UserDO selectByUsername(String username){
        UserDO query = new UserDO();
        query.setUsername(username);
        return selectOne(query);
    }

}
