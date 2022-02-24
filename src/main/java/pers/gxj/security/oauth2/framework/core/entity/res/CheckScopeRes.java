package pers.gxj.security.oauth2.framework.core.entity.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.gxj.security.oauth2.common.enums.CheckResult;

import java.util.Map;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 15:14
 */
@Data
public class CheckScopeRes {

    @ApiModelProperty(value = "验权的客户号")
    private String clientId;

    /**
     * @see CheckResult
     */
    @ApiModelProperty(value = "待检验的scope, 返回code(0:未授权，1：授权，2：未注册)")
    private Map<String, Integer> scopes;

    @ApiModelProperty(value = "待检验的功能模块列表（code）")
    private Map<String, Integer> modules;

    @ApiModelProperty(value = "待检验的接口列表（url）")
    private Map<String, Integer> interfaces;

}
