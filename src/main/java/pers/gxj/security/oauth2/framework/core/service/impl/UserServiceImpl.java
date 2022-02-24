package pers.gxj.security.oauth2.framework.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.gxj.security.oauth2.framework.core.service.UserService;
import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;
import pers.gxj.security.oauth2.framework.core.mapper.UserMapper;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 9:57
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,UserDO> implements UserService {


}
