package pers.gxj.security.oauth2.common.utils.decoratorchain.model.jdk;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/18 16:01
 */
public class Dog implements IAnimal {
    @Override
    public void bark() {
        System.out.println("woof!");
    }

    @Override
    public String getName() {
        bark();
        return "Doggy";
    }
}
