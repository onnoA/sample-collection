package com.onnoa.shop.common.pattern.templatemethod.demo3;

/**
 * @author onnoA
 * @date 2021年05月27日 20:06
 */
public class Cricket extends Game {

    @Override
    void endPlay() {
        System.out.println("Cricket Game Finished!");
    }

    @Override
    void voice() {
        System.out.println("Cricket Game voice is calmness ...");
    }

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }

    @Override
    protected boolean isVoice() {
        return false;
    }
}
