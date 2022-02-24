package pers.gxj.security.oauth2.framework.core.entity.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import pers.gxj.security.oauth2.framework.mybatis.BaseEntity;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 10:54
 */
@Data
@ApiModel(value = "用户")
@TableName("CLIENT_DETAILS")
public class ClientDO extends BaseEntity {

    @TableField(value = "CLIENT_ID")
    private String clientId;

    @TableField(value = "RESOURCE_IDS")
    private String resourceIds;

    @TableField(value = "CLIENT_SECRET")
    private String clientSecret;

    @TableField(value = "SCOPE")
    private String scope;

    @TableField(value = "AUTHORIZED_GRANT_TYPES")
    private String authorizedGrantTypes;

    @TableField(value = "WEB_SERVER_REDIRECT_URI")
    private String webServerRedirectUri;

    @TableField(value = "AUTHORITIES")
    private String authorities;

    @TableField(value = "ACCESS_TOKEN_VALIDITY")
    private Integer accessTokenValidity;

    @TableField(value = "REFRESH_TOKEN_VALIDITY")
    private Integer refreshTokenValidity;

    @TableField(value = "ADDITIONAL_INFORMATION")
    private String additionalInformation;

    @TableField(value = "AUTOAPPROVE")
    private Boolean autoApprove;

    @TableField(value = "DELETE_FLAG", fill = FieldFill.INSERT)
    private Boolean deleteFlag;

}
