package pers.gxj.security.oauth2.framework.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.gxj.security.oauth2.common.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 11:33
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    default T selectOne(T query) {
        QueryWrapper<T> qw = getQueryWrapper(query);
        return selectOne(qw);
    }

    default List<T> selectList(T query) {
        QueryWrapper<T> qw = getQueryWrapper(query);
        return selectList(qw);
    }


    static <T> QueryWrapper<T> getQueryWrapper(T query) {
        QueryWrapper<T> qw = new QueryWrapper<>();
        List<Field> fields = ReflectionUtils.getAnnotationField(query.getClass(), TableField.class);
        for (Field field : fields) {
            String jdbcField = ReflectionUtils.getAnnotationValue(field, TableField.class, TableField::value);
            Object fieldValue = ReflectionUtils.getFieldValue(field, query);
            if (fieldValue != null) {
                qw.eq(jdbcField, fieldValue);
            }
        }
        return qw;
    }
}
