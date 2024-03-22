package com.wjs.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 9:18
 */
@Data
public class R implements Serializable {

    private int code;

    private String message;

    private boolean flag;

    private Map<String, Object> data = new HashMap<>();


    public R() {
    }


    public static R ok() {
        R r = new R();
        r.setMessage("操作成功");
        r.setCode(CodeStatus.SUCCESS);
        r.setFlag(true);
        return r;
    }

    public static R error() {
        R r = new R();
        r.setMessage("操作失败");
        r.setCode(CodeStatus.ERROR);
        r.setFlag(false);
        return r;
    }

    public R code(int code) {
        this.code = code;
        return this;
    }

    public R message(String message) {
        this.message = message;
        return this;
    }

    public R flag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public R data(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.data.putAll(map);
        return this;
    }

}
