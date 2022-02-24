package pers.gxj.security.oauth2.framework.core.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import pers.gxj.security.oauth2.framework.core.entity.req.CheckScopeReq;
import pers.gxj.security.oauth2.framework.core.entity.res.CheckScopeRes;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 16:16
 */
public interface ScopeService {


    /**
     * 客户端查询用户授权的 SCOPE 是否可以访问请求参数中的 SCOPE 、功能模块、接口授权
     *
     * @param oauth2Auth 授权用户登录信息
     * @param req 权限校验请求
     * @return 权限校验结果
     */
    CheckScopeRes check(OAuth2Authentication oauth2Auth, CheckScopeReq req);
}
