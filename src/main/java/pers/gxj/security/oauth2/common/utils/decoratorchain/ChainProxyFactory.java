package pers.gxj.security.oauth2.common.utils.decoratorchain;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 11:02
 */
public class ChainProxyFactory<T> {

    public static <T> T createProxy(DecoratorChain<T> targetObject) {
        if (targetObject == null) {
            return null;
        }
        BaseChainProxy<T> chainProxy;
        if (targetObject.getEntityClass().isInterface()) {
            chainProxy = new JDKProxy<>(targetObject);
        } else {
            chainProxy = new CglibProxy<>(targetObject);
        }
        return (T) chainProxy.getProxy();
    }

    public static <T> T createCglibProxy(DecoratorChain<T> targetObject) {
        if (targetObject == null) {
            return null;
        }
        return (T) new CglibProxy<>(targetObject).getProxy();
    }

}
