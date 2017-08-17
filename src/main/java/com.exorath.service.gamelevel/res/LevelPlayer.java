package com.exorath.service.gamelevel.res;

import java.util.List;

/**
 * Created by toonsev on 8/16/2017.
 */
public class LevelPlayer {
    private String playerId;
    private String gameId;
    private int lvl;
    private int xp;
    private int totalXp;
    private List<Integer> consumable;

    public LevelPlayer(String playerId, String gameId, int lvl, int xp, int totalXp, List<Integer> consumable) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.lvl = lvl;
        this.xp = xp;
        this.totalXp = totalXp;
        this.consumable = consumable;
    }

    public LevelPlayer(String playerId, String gameId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public LevelPlayer() {}

    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getLvl() {
        return lvl;
    }

    public int getXp() {
        return xp;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public List<Integer> getConsumable() {
        return consumable;
    }
}
