package pers.gxj.security.oauth2.framework.core.mapper;

import pers.gxj.security.oauth2.framework.core.entity.dos.ClientDO;
import pers.gxj.security.oauth2.framework.mybatis.MyBaseMapper;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 9:30
 */
public interface ClientMapper extends MyBaseMapper<ClientDO> {

    default ClientDO selectByClientId(String clientId){
        ClientDO query = new ClientDO();
        query.setClientId(clientId);
        return selectOne(query);
    }
}
