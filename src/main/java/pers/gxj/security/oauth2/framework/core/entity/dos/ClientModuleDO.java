package pers.gxj.security.oauth2.framework.core.entity.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.gxj.security.oauth2.framework.mybatis.BaseEntity;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 11:32
 */
@Data
@ApiModel(value = "客户端功能模块表")
@TableName("CLIENT_MODULE")
public class ClientModuleDO extends BaseEntity {

    @TableField("CLIENT_ID")
    @ApiModelProperty(value = "客户端代码")
    private String clientId;

    @TableField("NAME")
    @ApiModelProperty(value = "功能模块名称")
    private String name;

    @TableField("CODE")
    @ApiModelProperty(value = "功能模块代码")
    private String code;

    @TableField("DESCRIPTION")
    @ApiModelProperty(value = "功能模块描述")
    private String description;
}
