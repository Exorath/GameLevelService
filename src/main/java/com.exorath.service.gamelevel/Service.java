package com.exorath.service.gamelevel;

import com.exorath.service.gamelevel.res.AddExperienceSuccess;
import com.exorath.service.gamelevel.res.LevelFunction;
import com.exorath.service.gamelevel.res.LevelPlayer;
import com.exorath.service.gamelevel.res.Success;

/**
 * Created by toonsev on 8/16/2017.
 */
public interface Service {
    AddExperienceSuccess addExperience(String gameId, String playerUuid, int exp);

    LevelPlayer getPlayer(String gameId, String playerUuid);

    Success consumeLevel(String gameId, String playerUuid, int level);

    LevelFunction getLevelFunction();
}
