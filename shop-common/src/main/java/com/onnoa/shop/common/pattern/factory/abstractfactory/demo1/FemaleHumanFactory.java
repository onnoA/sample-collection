package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class FemaleHumanFactory extends AbstractHumanFactory {

    @Override
    Human createWhiteHuman() {
        return new WhiteFemaleHuman();
    }

    @Override
    Human createYellowHuman() {
        return new YellowFemaleHuman();
    }

    @Override
    Human createBlackHuman() {
        return new BlackFemaleHuman();
    }
}
