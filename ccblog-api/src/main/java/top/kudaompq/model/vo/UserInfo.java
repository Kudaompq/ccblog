package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/9 18:34
 * @version: v1.0
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像路径
     */
    private String avatar;


    /**
     * 角色
     */
    private String role;

    /**
     * 邮箱
     */
    private String email;

}
