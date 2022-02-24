package pers.gxj.security.oauth2.framework.core.entity.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 9:24
 */
public class UserDTO {




    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "状态")
    private String status;
}
