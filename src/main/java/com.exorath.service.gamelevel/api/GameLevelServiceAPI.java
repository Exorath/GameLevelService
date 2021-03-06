package com.exorath.service.gamelevel.api;

import com.exorath.service.gamelevel.Service;
import com.exorath.service.gamelevel.res.*;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

import java.util.HashMap;

/**
 * Created by toonsev on 8/17/2017.
 */
public class GameLevelServiceAPI implements Service {

    private static final Gson GSON = new Gson();
    private String address;

    public GameLevelServiceAPI(String address) {
        this.address = address;
    }

    @Override
    public AddExperienceSuccess addExperience(String gameId, String playerUuid, int exp) {
        try {
            String body = Unirest.post(url("/games/{gameId}/players/{playerUuid}/xp/{xp}"))
                    .routeParam("gameId", gameId)
                    .routeParam("playerUuid", playerUuid)
                    .routeParam("xp", String.valueOf(exp))
                    .asString().getBody();
            return GSON.fromJson(body, AddExperienceSuccess.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new AddExperienceSuccess(e.getMessage(), -1);
        }
    }

    /**
     * May throw runtimeexceptions if shit stopped working
     */
    @Override
    public LevelPlayer getPlayer(String gameId, String playerUuid) throws RuntimeException {
        try {

            String body = Unirest.get(url("/games/{gameId}/players/{playerUuid}"))
                    .routeParam("gameId", gameId)
                    .routeParam("playerUuid", playerUuid)
                    .asString().getBody();
            return GSON.fromJson(body, LevelPlayer.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Success consumeLevel(String gameId, String playerUuid, int level) {
        try {
            String body = Unirest.post(url("/games/{gameId}/players/{playerUuid}/consume/{lvl}"))
                    .routeParam("gameId", gameId)
                    .routeParam("playerUuid", playerUuid)
                    .routeParam("lvl", String.valueOf(level))
                    .asString().getBody();
            return GSON.fromJson(body, Success.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Success(e.getMessage(), -1);
        }
    }

    @Override
    public LevelFunction getLevelFunction() {
        return new SimpleLevelFunction();
    }

    private String url(String endpoint) {
        return address + endpoint;
    }

    private static HashMap<String, GameLevelServiceAPI> instances = new HashMap<>();

    public static synchronized GameLevelServiceAPI getInstance(String address) {//allows for caching in the future
        GameLevelServiceAPI gameLevelServiceAPI = instances.get(address);
        if (gameLevelServiceAPI == null) {
            gameLevelServiceAPI = new GameLevelServiceAPI(address);
            instances.put(address, gameLevelServiceAPI);
        }
        return gameLevelServiceAPI;
    }
}
