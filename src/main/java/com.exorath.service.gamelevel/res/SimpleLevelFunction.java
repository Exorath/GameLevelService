package com.exorath.service.gamelevel.res;

/**
 * Created by toonsev on 8/16/2017.
 */
public class SimpleLevelFunction implements LevelFunction {
    private float base = 1.05f;
    private int baseMultiplier = 50;
    private int linearMultiplier = 20;

    @Override
    public int getRequiredXp(LevelPlayer levelPlayer) {
        return getXp(levelPlayer.getLvl() + 1) - levelPlayer.getXp();
    }

    @Override
    public int getXp(int forLevel) {
        return (int)(baseMultiplier * Math.pow(base,forLevel - 1 )) + linearMultiplier * (forLevel - 1);
    }
}
