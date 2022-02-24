package pers.gxj.security.oauth2.framework.core.service.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import pers.gxj.security.oauth2.common.enums.CheckResult;
import pers.gxj.security.oauth2.common.enums.ScopeRelType;
import pers.gxj.security.oauth2.common.utils.CollectionUtils;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientInterfaceDO;
import pers.gxj.security.oauth2.framework.core.entity.dos.ClientModuleDO;
import pers.gxj.security.oauth2.framework.core.entity.req.CheckScopeReq;
import pers.gxj.security.oauth2.framework.core.entity.res.CheckScopeRes;
import pers.gxj.security.oauth2.framework.core.mapper.ClientModuleMapper;
import pers.gxj.security.oauth2.framework.core.service.ClientInterfaceService;
import pers.gxj.security.oauth2.framework.core.service.ClientScopeRelService;
import pers.gxj.security.oauth2.framework.core.service.ScopeService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 16:16
 */
@Service
@Slf4j
public class ScopeServiceImpl implements ScopeService{

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private ClientScopeRelService clientScopeRelService;
    @Autowired
    private ClientModuleMapper clientModuleMapper;
    @Autowired
    private ClientInterfaceService clientInterfaceService;

    @Override
    public CheckScopeRes check(OAuth2Authentication oauth2Auth, CheckScopeReq req) {

        List<String> toCheckScopes = req.getScopes();
        List<String> toCheckModules = req.getModules();
        List<String> toCheckInterfaces = req.getInterfaces();
        String clientId = req.getClientId();
        //query
        Map<String, Integer> scopeAns = checkScope(oauth2Auth, clientId, toCheckScopes);
        Map<String, Integer> moduleAns = checkModule(oauth2Auth, clientId, toCheckModules);
        Map<String, Integer> interfaceAns = checkInterface(oauth2Auth, clientId, toCheckInterfaces);
        //return
        CheckScopeRes res = new CheckScopeRes();
        res.setClientId(req.getClientId());
        res.setModules(moduleAns);
        res.setInterfaces(interfaceAns);
        res.setScopes(scopeAns);
        return res;
    }

    private Map<String, Integer> checkInterface(OAuth2Authentication oauth2Auth, String clientId, List<String> toCheckInterfaces) {
        Set<String> authorizedScopes = oauth2Auth.getOAuth2Request().getScope();
        Map<String, Integer> interfaceAns = Maps.newHashMap();
        for (String methodUrl : toCheckInterfaces) {
            //查询客户端的接口ID
            ClientInterfaceDO interfaceDO = clientInterfaceService.selectByClientAndMethodUrl(clientId, methodUrl);
            //快速返回：未知的接口
            if (Objects.isNull(interfaceDO)) {
                interfaceAns.put(methodUrl, CheckResult.NOT_REGISTER.getCode());
                continue;
            }
            //查询有接口访问权限的SCOPE集合
            Set<String> scopes = clientScopeRelService.selectScopes(interfaceDO.getId(), ScopeRelType.INTERFACE);
            //取有权限的集合与用户授权集合的交集（结果保存再scopes中）
            scopes.retainAll(authorizedScopes);
            //返回结果：判断用户授权集合与访问权限集合交集是否为空
            if (CollectionUtils.isEmpty(scopes)) {
                interfaceAns.put(methodUrl, CheckResult.NOT_AUTHORIZED.getCode());
            } else {
                interfaceAns.put(methodUrl, CheckResult.AUTHORIZED.getCode());
            }
        }
        return interfaceAns;
    }

    private Map<String, Integer> checkModule(OAuth2Authentication oauth2Auth, String clientId, List<String> toCheckModules) {
        Set<String> authorizedScopes = oauth2Auth.getOAuth2Request().getScope();
        Map<String, Integer> moduleAns = Maps.newHashMap();
        for (String moduleCode : toCheckModules) {
            //查询客户端的模块ID
            ClientModuleDO moduleDO = clientModuleMapper.selectByClientAndCode(clientId, moduleCode);
            //快速返回：未知的模块
            if (Objects.isNull(moduleDO)) {
                moduleAns.put(moduleCode, CheckResult.NOT_REGISTER.getCode());
                continue;
            }
            //查询有模块访问权限的SCOPE集合
            Set<String> scopes = clientScopeRelService.selectScopes(moduleDO.getId(), ScopeRelType.MODULE);
            //取有权限的集合与用户授权集合的交集（结果保存再scopes中）
            scopes.retainAll(authorizedScopes);
            //返回结果：判断用户授权集合与访问权限集合交集是否为空
            if (CollectionUtils.isEmpty(scopes)) {
                moduleAns.put(moduleCode, CheckResult.NOT_AUTHORIZED.getCode());
            } else {
                moduleAns.put(moduleCode, CheckResult.AUTHORIZED.getCode());
            }
        }
        return moduleAns;
    }

    private Map<String, Integer> checkScope(OAuth2Authentication oauth2Auth, String clientId, List<String> toCheckScopes) {
        //读取客户端信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        //读取用户授权范围
        Set<String> authorizedScopes = oauth2Auth.getOAuth2Request().getScope();
        //读取客户端注册的授权范围
        Set<String> existsScopes = clientDetails.getScope();
        //返回值
        Map<String, Integer> scopeAns = Maps.newHashMap();
        //遍历待检验的scope
        for (String scope : toCheckScopes) {
            if (CollectionUtils.contains(authorizedScopes, scope)) {
                //在用户授权范围内，返回已授权
                scopeAns.put(scope, CheckResult.AUTHORIZED.getCode());
            } else if (CollectionUtils.contains(existsScopes, scope)) {
                //未在客户端注册的scope集合中找到，返回未注册
                scopeAns.put(scope, CheckResult.NOT_REGISTER.getCode());
            } else {
                //在客户端注册集合中，不在用户授权集合中，返回未授权
                scopeAns.put(scope, CheckResult.NOT_AUTHORIZED.getCode());
            }
        }
        return scopeAns;
    }

}
