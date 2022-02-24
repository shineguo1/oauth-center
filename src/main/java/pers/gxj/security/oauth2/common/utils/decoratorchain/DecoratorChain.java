package pers.gxj.security.oauth2.common.utils.decoratorchain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 10:28
 */
@Data
public class DecoratorChain<T> {

    private List<T> decorators = Lists.newArrayList();
    private Class<T> entityClass;


    public DecoratorChain(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static <T> DecoratorChain<T> create(Class<T> entityClass) {
        return new DecoratorChain<>(entityClass);
    }

    public DecoratorChain<T> append(T chainEntity) {
        decorators.add(chainEntity);
        return this;
    }


}
