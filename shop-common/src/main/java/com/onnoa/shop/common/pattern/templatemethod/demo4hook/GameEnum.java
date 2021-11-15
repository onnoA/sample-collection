package com.onnoa.shop.common.pattern.templatemethod.demo4hook;

/**
 * @author onnoA
 * @Description
 * @date 2021年08月18日 14:45
 */
public enum GameEnum {

    DEFAULT_FOOTBALL_GAME("FOOTBALL_GAME", "默认足球游戏配置", "com.onnoa.shop.common.pattern.templatemethod.demo4hook.Football"),
    CRICKET_GAME("CRICKET_GAME", "板球运动配置", "com.onnoa.shop.common.pattern.templatemethod.demo4hook.Cricket")
            ;



    private String gameType;

    private String gameName;

    private String classFullName;

    GameEnum(String gameType, String gameName, String classFullName) {
        this.gameType = gameType;
        this.gameName = gameName;
        this.classFullName = classFullName;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }
}
