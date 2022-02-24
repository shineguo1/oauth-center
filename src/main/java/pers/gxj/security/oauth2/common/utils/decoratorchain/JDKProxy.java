package pers.gxj.security.oauth2.common.utils.decoratorchain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 11:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JDKProxy<T> extends BaseChainProxy<T> implements InvocationHandler {

    public JDKProxy(DecoratorChain<T> chain){
        super(chain);
    }

    @Override
    public Object getProxy() {//将目标对象传入进行代理
        return Proxy.newProxyInstance(
                super.getEntityClass().getClassLoader(),
                new Class[]{super.getEntityClass()},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invokeChain(proxy, method, args);
    }

}
