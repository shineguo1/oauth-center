package pers.gxj.security.oauth2.framework.core.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 15:14
 */
@Data
public class CheckScopeReq {

    @ApiModelProperty(value = "验权的客户号")
    private String clientId;

    @ApiModelProperty(value = "待检验的scope")
    private List<String> scopes;

    @ApiModelProperty(value = "待检验的功能模块列表（code）")
    private List<String> modules;

    @ApiModelProperty(value = "待检验的接口列表（格式：`[请求方式] URL`, 例如 `GET http://www.baidu.com` 以及 `http://www.baidu.com`）")
    private List<String> interfaces;

}
