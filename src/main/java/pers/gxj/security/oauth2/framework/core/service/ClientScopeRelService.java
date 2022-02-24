package pers.gxj.security.oauth2.framework.core.service;

import pers.gxj.security.oauth2.common.enums.ScopeRelType;

import java.util.Set;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 14:39
 */
public interface ClientScopeRelService {

    /**
     * 查询哪些授权范围(return)可以访问指定功能模块/接口(relId)
     *
     * @param relId 关联表主键
     * @param relType 关联类型
     * @return 返回关联的授权范围集合
     */
    Set<String> selectScopes(Long relId, ScopeRelType relType);
}
