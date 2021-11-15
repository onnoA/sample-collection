package com.onnoa.shop.common.pattern.templatemethod.demo2;

/**
 * @author onnoA
 * @date 2021年05月27日 20:07
 */
public class Client {
    public static void main(String[] args) {
        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
