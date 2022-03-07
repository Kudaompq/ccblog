package top.kudaompq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章首图，用于随机文章展示
     */
    private String firstPicture;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 描述
     */
    private String description;

    /**
     * 公开或私密
     */
    @TableField(value = "is_published")
    private Boolean published;

    /**
     * 推荐开关
     */
    @TableField(value = "is_recommend")
    private Boolean recommend;

    /**
     * 赞赏开关
     */
    @TableField(value = "is_appreciation")
    private Boolean appreciation;

    /**
     * 评论开关
     */
    @TableField(value = "is_comment_enabled")
    private Boolean commentEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 浏览次数
     */
    private Integer views;


    /**
     * 文章分类
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    @TableField(value = "is_top")
    private Boolean top;

    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private List<Tag> tagList = new ArrayList<>();




}
