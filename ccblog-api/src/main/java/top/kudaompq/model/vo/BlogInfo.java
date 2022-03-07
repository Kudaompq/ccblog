package top.kudaompq.model.vo;

import lombok.Data;
import top.kudaompq.entity.Category;
import top.kudaompq.entity.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 博客简要信息
 * @author: kudaompq
 * @date: 2022/1/30 15:27
 * @version: v1.0
 */

@Data
public class BlogInfo implements Serializable {

    private Long id;
    private String title;//文章标题
    private String description;//描述
    private String firstPicture;//图片
    private LocalDateTime createTime;//创建时间
    private Integer views;//浏览次数
    private Boolean top;//是否置顶

    private Category category;//文章分类
    private List<Tag> tagList = new ArrayList<>();//文章标签

}
