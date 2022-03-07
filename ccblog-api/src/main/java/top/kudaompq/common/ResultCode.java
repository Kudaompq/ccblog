package top.kudaompq.common;

/**
 * @description: 返回常量编号
 * @author: kudaompq
 * @date: 2022/1/4 16:59
 * @version: v1.0
 */

public class ResultCode {

    /**
     * 服务器成功返回用户请求的数据
     */
    public static final int SUCCESS = 200;

    /**
     * 用户没有权限
     */
    public static final int UN_AUTHORIZED = 401;

    /**
     * 用户禁止访问
     */
    public static final int FORBIDDEN = 403;

    /**
     * 请求不存在
     */
    public static final int NOT_FIND = 404;

    /**
     * 服务器发生错误
     */
    public static final int SERVER_ERROR = 500;
}
