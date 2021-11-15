package com.onnoa.shop.common.pattern.templatemethod.demo2;

/**
 * @author onnoA
 * @date 2021年05月27日 20:07
 */
public class Football extends Game {

    @Override
    void endPlay() {
        System.out.println("Football Game Finished!");
    }

    @Override
    void initialize() {
        System.out.println("Football Game Initialized! Start playing.");
    }

    @Override
    void startPlay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }
}