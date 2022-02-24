package pers.gxj.security.oauth2.common.utils.decoratorchain;

import org.junit.Test;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.cglib.Animal;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.cglib.Cat2;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.cglib.Dog2;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.jdk.IAnimal;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.jdk.Cat;
import pers.gxj.security.oauth2.common.utils.decoratorchain.model.jdk.Dog;

/**
 * Created by xinjie_guo on 2022/2/18.
 */
public class ChainProxyFactoryTest {

    @Test
    public void test_jdk_proxy() {
        DecoratorChain<IAnimal> chain = new DecoratorChain<>(IAnimal.class)
                .append(new Cat())
                .append(new Dog());
        IAnimal proxy = ChainProxyFactory.createProxy(chain);
//        IAnimal proxy = (IAnimal) new CglibProxy(chain).getProxy();
        proxy.bark();
        System.out.println(proxy.getName());
    }

    @Test
    public void test_cglib_proxy() {
        DecoratorChain<Animal> chain = DecoratorChain.create(Animal.class)
                .append(new Cat2())
                .append(new Dog2());
        Animal proxy = ChainProxyFactory.createProxy(chain);
        proxy.bark();
        System.out.println(proxy.getName());
    }
}