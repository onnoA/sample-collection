package com.onnoa.shop.common.pattern.templatemethod.demo4hook;

import java.util.HashMap;
import java.util.Map;

/**
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

    // 基本方法
    abstract void voice();

    // 钩子方法，默认游戏的声音是关闭
    protected boolean isVoice() {
        return false;
    }


    protected Map<String, Object> hook() {
        return new HashMap<>();
    }

    //模板
    public final void play() {

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        if (isVoice()) {
            voice();
        }

        //结束游戏
        endPlay();

        // 测试 hook 钩子方法
        hook();
    }
}
