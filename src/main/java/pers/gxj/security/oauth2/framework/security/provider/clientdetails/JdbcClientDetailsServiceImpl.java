package pers.gxj.security.oauth2.framework.security.provider.clientdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import pers.gxj.security.oauth2.common.exception.BizException;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientDO;
import pers.gxj.security.oauth2.framework.core.mapper.ClientMapper;

import java.util.Objects;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2021/9/28 17:00
 */
@Component("jdbcClientDetailsService")
public class JdbcClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private ClientMapper clientMapper;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDO clientDO = clientMapper.selectByClientId(clientId);
        if(Objects.isNull(clientDO)){
            throw new BizException("客户端不存在。");
        }
        //密码是经过加密的 {@link BCryptPasswordEncoder#encode}
        return new AuthClient(clientDO);

    }

}
