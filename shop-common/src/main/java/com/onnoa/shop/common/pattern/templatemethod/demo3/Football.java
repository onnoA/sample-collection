package com.onnoa.shop.common.pattern.templatemethod.demo3;

/**
 * @author onnoA
 * @date 2021年05月27日 20:07
 */
public class Football extends Game {

    private boolean isVoice = true;

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }

    @Override
    void voice() {
        System.out.println("Football Game voice is exciting ...");
    }

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }

    @Override
    protected boolean isVoice() {
        return isVoice;
    }

    public void setVoice(boolean isVoice) {
        this.isVoice = isVoice;
    }
}