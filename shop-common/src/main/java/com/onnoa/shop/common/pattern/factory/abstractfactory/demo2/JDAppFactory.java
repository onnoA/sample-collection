package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:21
 */
public class JDAppFactory extends AbstractOSFactory {

    @Override
    OS createWindowsOs() {
        return new WindowsJDApp();
    }

    @Override
    OS createLinuxOs() {
        return new LinuxJDApp();
    }

    @Override
    OS createAndroidOs() {
        return new AndroidJDApp();
    }
}
