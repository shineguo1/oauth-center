package pers.gxj.security.oauth2.common.utils.decoratorchain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 11:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CglibProxy<T> extends BaseChainProxy<T> implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public CglibProxy(DecoratorChain<T> chain){
        super(chain);
    }


    @Override
    public Object getProxy() {
        //设置需要创建子类的类
        enhancer.setSuperclass(getEntityClass());
        enhancer.setCallback(this);
        //通过字节码技术动态创建子类实例
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //通过代理类调用父类中的方法
        return super.invokeChain(obj, method,args);
    }
}