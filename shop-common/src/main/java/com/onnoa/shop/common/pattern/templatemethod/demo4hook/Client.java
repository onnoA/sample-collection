package com.onnoa.shop.common.pattern.templatemethod.demo4hook;

/**
 * @author onnoA
 * @date 2021年05月27日 20:07
 */
public class Client {

    public static void main(String[] args) {
//        Game game = new Cricket();
//        game.play();
//        System.out.println("===========================================");
//        Football football = new Football();
//        football.setVoice(true);
//        football.play();

        Game football = GameFactory.getGame("FOOTBALL_GAME");
        football.play();

        System.out.println("========================================");

        Game cricket_game = GameFactory.getGame("CRICKET_GAME");
        cricket_game.play();


    }
}
