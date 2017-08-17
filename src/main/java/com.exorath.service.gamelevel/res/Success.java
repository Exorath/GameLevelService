package com.exorath.service.gamelevel.res;

/**
 * Created by toonsev on 8/16/2017.
 */
public class Success {
    private boolean success;
    private String error;
    private Integer code;

    public Success(boolean success) {
        this.success = success;
    }

    public Success(String error, Integer code) {
        success = false;
        this.error = error;
        this.code = code;
    }

    public Success(boolean success, String error, Integer code) {
        this.success = success;
        this.error = error;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public Integer getCode() {
        return code;
    }
}
