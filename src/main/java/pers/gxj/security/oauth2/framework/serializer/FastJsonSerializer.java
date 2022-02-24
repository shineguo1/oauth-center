package pers.gxj.security.oauth2.framework.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import pers.gxj.security.oauth2.framework.cache.config.OAuth2AuthenticationSerializer;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/22 16:24
 */
@Component
public class FastJsonSerializer {

    static {
        //开启全局autoType（即"@Type"）非常危险，因为黑名单穷举不玩，所以使用白名单实现
        ParserConfig.getGlobalInstance().setAutoTypeSupport(false);
        //禁用循环引用(防止反序列化出错)
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        //@Type的白名单
        ParserConfig.getGlobalInstance().addAccept("pers.gxj.");
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication", OAuth2Authentication.class);
        //特殊的序列化器(OAuth2Authentication的属性与getter方法名不一致，需要借助构造函数反序列化)
        ParserConfig.getGlobalInstance().putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
    }

    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(object, SerializerFeature.WriteClassName);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            //默认使用全部配置
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class);
        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
    }
}
