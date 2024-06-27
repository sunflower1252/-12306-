package com.sanzuniao.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 *
 * @author yg
 * @date 2024/04/19
 */
@Data
public class Result implements Serializable {
    /**
     * success 200 500 401 404\
     * msg 信息
     * data 数据
     */

    private boolean success;

    private String message;

    private Object context;

    public static Result success(Object object) {
        Result r = new Result();
        r.context = object;
        r.success = true;
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.message = msg;
        r.success = false;
        return r;
    }


}
