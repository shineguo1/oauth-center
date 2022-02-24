package pers.gxj.security.oauth2.framework.security.provider.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.gxj.security.oauth2.common.exception.BizException;
import pers.gxj.security.oauth2.framework.core.entity.dos.UserDO;
import pers.gxj.security.oauth2.framework.core.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 17:00
 */
@Service
public class JdbcUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查数据库 return user.username = xxx, user.password = xxx, 返回 UserDetails对象
        UserDO userDO = userMapper.selectByUsername(username);
        if(Objects.isNull(userDO)){
            throw new BizException("用户名或密码错误。");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_DEFAULT");
        //密码是经过加密的 {@link BCryptPasswordEncoder#encode}
        return new AuthUser(userDO, authorities);
    }
}
