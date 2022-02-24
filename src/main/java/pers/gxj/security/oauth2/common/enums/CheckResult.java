package pers.gxj.security.oauth2.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/23 15:20
 */
@AllArgsConstructor
@Getter
public enum CheckResult {

    NOT_AUTHORIZED(0),
    AUTHORIZED(1),
    NOT_REGISTER(2);

    private int code;
}
