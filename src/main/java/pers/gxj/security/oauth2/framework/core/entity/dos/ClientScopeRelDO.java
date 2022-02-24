package pers.gxj.security.oauth2.framework.core.entity.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.oauth2.provider.ClientDetails;
import pers.gxj.security.oauth2.framework.mybatis.BaseEntity;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/24 11:32
 */
@Data
@ApiModel(value = "客户端功能模块表")
@TableName("CLIENT_SCOPE_REL")
public class ClientScopeRelDO extends BaseEntity {

    /**
     * @see pers.gxj.security.oauth2.common.enums.ScopeRelType
     */
    @TableField("TYPE")
    @ApiModelProperty(value = "关联类型(INTERFACE, MODULE)")
    private String type;

    /**
     * @see ClientModuleDO
     * @see ClientInterfaceDO
     */
    @TableField("REL_ID")
    @ApiModelProperty(value = "关联ID")
    private Long relId;

    /**
     * @see ClientDetails#getScope()
     */
    @TableField("SCOPE")
    @ApiModelProperty(value = "授权范围")
    private String scope;

}
