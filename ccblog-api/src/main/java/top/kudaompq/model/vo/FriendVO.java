package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/2 16:23
 * @version: v1.0
 */

@Data
public class FriendVO implements Serializable {
    private Long id;
    private String nickname;//昵称
    private String description;//描述
    private String website;//站点
    private String avatar;//头像
}
