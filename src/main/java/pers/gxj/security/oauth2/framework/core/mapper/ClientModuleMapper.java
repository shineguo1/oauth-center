package pers.gxj.security.oauth2.framework.core.mapper;

import pers.gxj.security.oauth2.framework.core.entity.dos.ClientModuleDO;
import pers.gxj.security.oauth2.framework.mybatis.MyBaseMapper;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 14:18
 */
public interface ClientModuleMapper extends MyBaseMapper<ClientModuleDO> {

    default ClientModuleDO selectByClientAndCode(String clientId, String code){
        ClientModuleDO query = new ClientModuleDO();
        query.setClientId(clientId);
        query.setCode(code);
        return selectOne(query);
    }
}
