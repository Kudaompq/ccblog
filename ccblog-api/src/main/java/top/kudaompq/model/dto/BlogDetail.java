package top.kudaompq.model.dto;

import lombok.Data;
import top.kudaompq.entity.Category;
import top.kudaompq.entity.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/31 23:38
 * @version: v1.0
 */

@Data
public class BlogDetail implements Serializable {
    private Long id;
    private String title;//文章标题
    private String firstPicture;//文章首图，用于随机文章展示
    private String content;//文章正文
    private String description;//描述
    private Boolean published;//公开或私密
    private Boolean recommend;//推荐开关
    private Boolean appreciation;//赞赏开关
    private Boolean commentEnabled;//评论开关
    private Boolean top;//是否置顶
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    private Integer views;//浏览次数

    private Category category;//文章分类
    private List<Tag> tags = new ArrayList<>();//文章标签

    private Object categoryId;//页面展示层传输的分类对象：正常情况下为 字符串 或 分类id
    private List<Object> tagList = new ArrayList<>();//页面展示层传输的标签对象：正常情况下为 List<Integer>标签id 或 List<String>标签名
}
