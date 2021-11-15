package com.onnoa.shop.common.pattern.factory.demo1;

public abstract class Client implements Human {

    public static void main(String[] args) {
        AbstractCreateHumanBeanFactory humanBeanFactory = new HumanBeanFactory();
        WhiteHuman humanBean = humanBeanFactory.createHumanBean(WhiteHuman.class);
        humanBean.color();
        humanBean.language();

        BlackHuman blHuman = humanBeanFactory.createHumanBean(BlackHuman.class);
        blHuman.color();
        blHuman.language();

        YellowHuman yellowHuman = humanBeanFactory.createHumanBean(YellowHuman.class);
        yellowHuman.color();
        yellowHuman.language();
    }
}
