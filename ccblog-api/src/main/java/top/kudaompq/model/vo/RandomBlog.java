package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 随机推荐博客
 * @author: kudaompq
 * @date: 2022/1/30 16:20
 * @version: v1.0
 */

@Data
public class RandomBlog  implements Serializable {
    private Long id;
    private String title;//文章标题
    private String firstPicture;//文章首图，用于随机文章展示
    private Date createTime;//创建时间
}
