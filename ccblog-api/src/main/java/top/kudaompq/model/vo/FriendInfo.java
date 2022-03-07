package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 友链信息
 * @author: kudaompq
 * @date: 2022/2/2 16:14
 * @version: v1.0
 */

@Data
public class FriendInfo  implements Serializable {

    private String content;
    private Boolean commentEnabled;
}
