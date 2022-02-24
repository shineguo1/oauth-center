package pers.gxj.security.oauth2.framework.mybatis.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import pers.gxj.security.oauth2.framework.security.util.SecurityUtils;

import java.util.Date;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 14:07
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String username = SecurityUtils.getUsernameOrDefault("SYSTEM");
        this.setFieldValByName("createdBy", username, metaObject);
        this.setFieldValByName("createdAt", new Date(), metaObject);
        this.setFieldValByName("updatedBy", username, metaObject);
        this.setFieldValByName("updatedAt", new Date(), metaObject);
        //有值，则写入
        if (metaObject.hasGetter("deleteFlag")) {
            this.setFieldValByName("deleteFlag", false, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        String username = SecurityUtils.getUsernameOrDefault("SYSTEM");
        this.setFieldValByName("updatedBy", username, metaObject);
        this.setFieldValByName("updatedAt", new Date(), metaObject);
    }
}

