package com.zzq.springbootdemo.dto;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zzq
 * Date: 2018-04-11
 * Time: 21:21
 */
public class JsonResult<T> {
    private static final String OK = "ok";
    private static final String ERROR = "error";
    private T data;
    private Meta meta;
    public JsonResult success() {
        this.meta = new Meta(true, OK);
        return this;
    }
    public JsonResult success(String message) {
        this.meta = new Meta(true, message);
        return this;
    }

    public JsonResult success(T data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }
    public JsonResult failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public JsonResult failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }
    public JsonResult failure(T data) {
        this.meta = new Meta(false, ERROR);
        this.data = data;
        return this;
    }
    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }
}
