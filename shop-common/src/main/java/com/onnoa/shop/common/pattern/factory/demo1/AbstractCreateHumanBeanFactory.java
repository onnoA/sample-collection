package com.onnoa.shop.common.pattern.factory.demo1;

public abstract class AbstractCreateHumanBeanFactory extends YellowHuman {

    public abstract <T extends Human> T createHumanBean(Class<T> clazz);


}
