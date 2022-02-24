package pers.gxj.security.oauth2.common.utils.decoratorchain;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 13:50
 */
@Data
public abstract class BaseChainProxy<T> {

    // ~ Instance fields
    // ========================================================================================================

    /**
     * 有序链
     */
    private DecoratorChain<T> chainObject;
    /**
     * 有序链的元素类型
     */
    private Class<T> entityClass;

    // ~ Constructors
    // ========================================================================================================

    BaseChainProxy(DecoratorChain<T> targetObject) {
        this.chainObject = targetObject;
        this.entityClass = targetObject.getEntityClass();
    }

    // ~ Abstract methods
    // ========================================================================================================

    /**
     * 返回链式模式的代理。
     * 代理类型是链式泛型的子类/实现，我们可以像直接使用泛型对象一样使用chain。
     *
     * @return 返回代理（泛型T的子类或者实现）
     */
    abstract Object getProxy();

    // ~ Methods
    // ========================================================================================================

    /**
     * 链式调用代理对象。
     * 返回值是void的认为是写方法，对chain中每一个对象都执行一遍。
     * 返回值非void的认为是读方法，按顺序遍历chain，找到第一个非空值立即返回。
     *
     * @param proxy  代理对象
     * @param method 执行方法
     * @param args   执行参数
     */
    public Object invokeChain(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
        Object ret = null;
        if (Void.TYPE.equals(method.getReturnType())) {
            doWrite(proxy, method, args);
        } else {
            ret = doRead(proxy, method, args);
        }
        return ret;
    }

    private void doWrite(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        for (Object decorator : chainObject.getDecorators()) {
            method.invoke(decorator, args);
        }
    }

    private Object doRead(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        for (Object decorator : chainObject.getDecorators()) {
            Object ret = method.invoke(decorator, args);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

}
