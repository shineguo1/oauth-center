package pers.gxj.security.oauth2.framework.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 9:43
 */
@Configuration
@MapperScan("pers.gxj.security.oauth2.framework.*.mapper")
public class MybatisConfig {
}
