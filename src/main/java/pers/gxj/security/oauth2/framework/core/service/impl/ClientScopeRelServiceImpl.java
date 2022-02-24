package pers.gxj.security.oauth2.framework.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gxj.security.oauth2.common.enums.ScopeRelType;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientScopeRelDO;
import pers.gxj.security.oauth2.framework.core.mapper.ClientScopeRelMapper;
import pers.gxj.security.oauth2.framework.core.service.ClientScopeRelService;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 14:39
 */
@Service
@Slf4j
public class ClientScopeRelServiceImpl  implements ClientScopeRelService {

    @Autowired
    private ClientScopeRelMapper clientScopeRelMapper;

    @Override
    public Set<String> selectScopes(Long relId, ScopeRelType relType){
        ClientScopeRelDO query = new ClientScopeRelDO();
        query.setRelId(relId);
        query.setType(relType.name());
        return clientScopeRelMapper.selectList(query).stream().map(ClientScopeRelDO::getScope).collect(Collectors.toSet());
    }
}
