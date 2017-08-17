package com.exorath.service.gamelevel.res;

/**
 * Created by toonsev on 8/16/2017.
 */
public interface LevelFunction {
    /**
     * Lower then zero means the player can level up
     * @param levelPlayer
     * @return
     */
    int getRequiredXp(LevelPlayer levelPlayer);

    int getXp(int forLevel);
}
