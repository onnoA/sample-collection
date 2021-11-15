package com.onnoa.shop.common.pattern.templatemethod.demo2;

/**
 *  游戏模板方法类
 * @author onnoA
 * @date 2021年05月27日 20:06
 */
public abstract class Game {

    // 基本方法
    abstract void initialize();

    // 基本方法
    abstract void startPlay();

    // 基本方法
    abstract void endPlay();

    //模板方法
    public final void play(){

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }
}
