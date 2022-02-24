package pers.gxj.security.oauth2.framework.core.entity.dos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.gxj.security.oauth2.framework.mybatis.BaseEntity;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 10:54
 */
@Data
@ApiModel(value = "用户")
@TableName("USER")
@NoArgsConstructor
@AllArgsConstructor
public class UserDO extends BaseEntity {

    @TableField("USER_NAME")
    @ApiModelProperty(value = "用户名")
    private String username;

    @TableField("PASSWORD")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableField("STATUS")
    @ApiModelProperty(value = "状态")
    private String status;

}
