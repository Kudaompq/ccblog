package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/3 15:40
 * @version: v1.0
 */

@Data
public class PageComment  implements Serializable {

    private Long id;
    private String nickname;//昵称
    private String content;//评论内容
    private String avatar;//头像(图片路径)
    private LocalDateTime createTime;//评论时间
    private String website;//个人网站
    private Boolean adminComment;//博主回复
    private String parentCommentId;//父评论id
    private String parentCommentNickname;//父评论昵称

    private List<PageComment> replyComments = new ArrayList<>();//回复该评论的评论
}
