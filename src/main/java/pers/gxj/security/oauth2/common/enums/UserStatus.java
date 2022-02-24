package pers.gxj.security.oauth2.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 14:00
 */
@AllArgsConstructor
public enum UserStatus {
    /**
     * 正常
     */
    NORMAL,
    /**
     * 锁定
     */
    LOCK,
    /**
     * 注销
     */
    DELETE
}
