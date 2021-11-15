package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:14
 */
public class TaoBaoAppFactory extends AbstractOSFactory {

    @Override
    OS createWindowsOs() {
        return new WindowsTaoBaoApp();
    }

    @Override
    OS createLinuxOs() {
        return new LinuxTaoBaoApp();
    }

    @Override
    OS createAndroidOs() {
        return new AndroidTaoBaoApp();
    }
}
