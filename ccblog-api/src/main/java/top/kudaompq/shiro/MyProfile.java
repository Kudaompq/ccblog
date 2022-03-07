package top.kudaompq.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: MyProfile
 * @Description: TODO
 * @author: kudaompq
 * @date: 2022/1/4  15:29
 */
@Data
public class MyProfile implements Serializable {

    private Integer id;
    private String username;
    private String nickname;
    private String avatar;

    public Integer getId(){return id;}



}
