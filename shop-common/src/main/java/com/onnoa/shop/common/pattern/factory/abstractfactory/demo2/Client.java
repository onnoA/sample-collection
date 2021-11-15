package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

public class Client {

    public static void main(String[] args) {
        // TaoBao App 工厂
        TaoBaoAppFactory taoBaoAppFactory = new TaoBaoAppFactory();
        OS windowsOs = taoBaoAppFactory.createWindowsOs();
        windowsOs.welcomeScreen();
        windowsOs.runningApp();

        // JD App 工厂
        JDAppFactory jdAppFactory = new JDAppFactory();
        OS androidOs = jdAppFactory.createAndroidOs();
        androidOs.welcomeScreen();
        androidOs.runningApp();


    }
}
