package com.exorath.service.gamelevel.res;

/**
 * Created by toonsev on 8/16/2017.
 */
public class AddExperienceSuccess extends Success {
    private boolean levelUp;

    public AddExperienceSuccess(boolean levelUp) {
        super(true);
        this.levelUp = levelUp;
    }

    public AddExperienceSuccess(String error, Integer code) {
        super(error, code);
    }
}
