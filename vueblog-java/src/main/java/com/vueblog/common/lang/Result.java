package com.vueblog.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @author m
 * @version 1.0
 * @className Result
 * @description 统一结果封装
 * @date 2021/1/31
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 3750976509624393391L;

    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        return success(200,"请求成功",data);
    }

    public static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg){
        return fail(400,msg,null);
    }

    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }

    public static Result fail(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
