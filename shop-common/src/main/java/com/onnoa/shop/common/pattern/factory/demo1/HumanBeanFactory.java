package com.onnoa.shop.common.pattern.factory.demo1;

public class HumanBeanFactory extends AbstractCreateHumanBeanFactory {

    @Override
    public <T extends Human> T createHumanBean(Class<T> clazz) {
        Human human = null;
        try {
            human = (T) Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) human;
    }
}
