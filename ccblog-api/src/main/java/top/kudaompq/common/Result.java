package top.kudaompq.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/1 17:22
 * @version: v1.0
 */

@Data
public class Result implements Serializable {

    private int code; // 200 是正常，非200两百表示异常
    private String msg;
    private Object data;

    public static Result success(Object data){
        return success(ResultCode.SUCCESS,"操作成功",data);
    }
    public static Result success(String msg,Object data){
        return success(ResultCode.SUCCESS,msg,data);
    }
    public static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result error(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result error(String msg,Object data){
       return error(400,msg,data);
    }

    public static Result error(Integer code,String msg){
        return error(code,msg,null);
    }
    public static Result error(String msg){
        return error(400,msg,null);
    }


}
