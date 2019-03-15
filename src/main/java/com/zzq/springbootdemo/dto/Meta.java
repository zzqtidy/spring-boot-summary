package com.zzq.springbootdemo.dto;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zzq
 * Date: 2018-04-11
 * Time: 21:10
 */
public class Meta {
    private boolean success;
    private String message;

    public Meta(boolean success) {
        this.success = success;
    }

    public Meta(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
