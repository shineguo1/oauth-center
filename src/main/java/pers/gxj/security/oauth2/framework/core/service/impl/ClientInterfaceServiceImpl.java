package pers.gxj.security.oauth2.framework.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gxj.security.oauth2.common.exception.BizException;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientInterfaceDO;
import pers.gxj.security.oauth2.framework.core.mapper.ClientInterfaceMapper;
import pers.gxj.security.oauth2.framework.core.service.ClientInterfaceService;
import pers.gxj.security.oauth2.framework.mybatis.MyBaseMapper;

import java.util.Set;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 16:57
 */
@Service
@Slf4j
public class ClientInterfaceServiceImpl implements ClientInterfaceService {

    @Autowired
    private ClientInterfaceMapper clientInterfaceMapper;

    @Override
    public ClientInterfaceDO selectByClientAndMethodUrl(String clientId, String methodUrl) {

        ClientInterfaceDO query = new ClientInterfaceDO();
        query.setClientId(clientId);
        //把
        String[] split = StringUtils.split(methodUrl.trim(), " ");
        switch (split.length) {
            case 1:
                query.setUrl(split[0]);
                QueryWrapper<ClientInterfaceDO> qw = MyBaseMapper.getQueryWrapper(query);
                qw.isNull("METHOD");
                return clientInterfaceMapper.selectOne(qw);
            case 2:
                query.setMethod(split[0]);
                query.setUrl(split[1]);
                return clientInterfaceMapper.selectOne(query);
            default:
                throw new BizException("接口url格式错误");
        }
    }

}
