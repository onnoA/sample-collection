package com.onnoa.shop.common.pattern.templatemethod.demo4hook;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author onnoA
 * @Description
 * @date 2021年08月18日 11:18
 */
public class GameFactory {

    private static Map<String, Game> templateMap = Maps.newConcurrentMap();

//    static {
//        templateMap.put("football", new Football());
//        templateMap.put("cricket", new Cricket());
//    }

    static {
        for (GameEnum game : GameEnum.values()) {
            try {
                Class<?> aClass = Class.forName(game.getClassFullName());
                templateMap.put(game.getGameType(), (Game) aClass.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static Game getGame(String template){
        return templateMap.get(template);
    }
}
