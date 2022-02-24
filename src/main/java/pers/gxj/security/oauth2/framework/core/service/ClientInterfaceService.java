package pers.gxj.security.oauth2.framework.core.service;

import pers.gxj.security.oauth2.framework.core.entity.dos.ClientInterfaceDO;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 16:57
 */
public interface ClientInterfaceService {
    ClientInterfaceDO selectByClientAndMethodUrl(String clientId, String methodUrl);
}
