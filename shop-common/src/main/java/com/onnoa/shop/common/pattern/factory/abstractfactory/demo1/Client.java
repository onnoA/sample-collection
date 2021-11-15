package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class Client {

    public static void main(String[] args) {

        // 男性工厂
        MaleHumanFactory maleHumanFactory = new MaleHumanFactory();
        // 女性工厂
        FemaleHumanFactory femaleHumanFactory = new FemaleHumanFactory();

        Human yellowHuman = maleHumanFactory.createYellowHuman();
        yellowHuman.sex();
        yellowHuman.color();
        yellowHuman.language();

        System.out.println("分隔符 =======================================");

        Human whiteHuman = femaleHumanFactory.createWhiteHuman();
        whiteHuman.color();
        whiteHuman.sex();
        whiteHuman.language();


    }
}
