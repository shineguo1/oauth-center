package pers.gxj.security.oauth2.framework.cache.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import pers.gxj.security.oauth2.framework.serializer.FastJsonSerializer;

/**
 * 对fastJson内置的GenericFastJsonRedisSerializer做扩展，使用全局配置ParserConfig.getGlobalInstance()代替私有配置defaultRedisConfig
 *
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/22 13:56
 * @see <a href="https://www.cnblogs.com/meow-world/articles/15192758.html">Spring Security Oauth2:RedisTokenStore之JSON序列化</a>
 */
public class GenericFastJsonRedisSerializer extends FastJsonSerializer implements RedisSerializer<Object> {

}
