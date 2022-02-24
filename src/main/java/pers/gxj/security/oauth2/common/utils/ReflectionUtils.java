package pers.gxj.security.oauth2.common.utils;

import com.google.common.collect.Lists;
import pers.gxj.security.oauth2.common.exception.BizException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/15 14:04
 */
public class ReflectionUtils {

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BizException("反射生成对象异常，class:" + clazz.getName());
        }
    }


    /**
     * 返回一个类及父类中所有被某个注解标注的属性
     *
     * @param clazz           类
     * @param annotationClass 注解类型
     * @return clazz类及父类中所有被annotationClass注解标注的属性Field
     */
    public static <T extends Annotation> List<Field> getAnnotationField(Class clazz, Class<T> annotationClass) {
        List<Field> list = Lists.newArrayList();
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                T annotation = field.getAnnotation(annotationClass);
                if (annotation != null) {
                    list.add(field);
                }
            }
        }
        return list;
    }

    /**
     * 返回一个属性上的某个注解的某个属性值
     *
     * @param field           类的一个属性
     * @param annotationClass 注解类型
     * @param annotationFunc  注解的方法
     * @return 返回field属性上annotationClass注解的annotationFunc属性值
     */
    public static <T extends Annotation, R> R getAnnotationValue(Field field, Class<T> annotationClass, Function<T, R> annotationFunc) {
        T annotation = field.getAnnotation(annotationClass);
        return annotationFunc.apply(annotation);
    }

    /**
     * 返回对象的某属性的值
     *
     * @param field  类的一个属性
     * @param object 类的一个对象
     * @return object的field的值
     */
    public static <T> Object getFieldValue(Field field, T object) {
        field.setAccessible(true);
        Object filedValue;
        try {
            filedValue = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return filedValue;
    }


}
