package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 归档页面博客简要信息
 * @author: kudaompq
 * @date: 2022/2/3 13:46
 * @version: v1.0
 */

@Data
public class ArchiveBlogVO implements Serializable {
    private Long id;
    private String title;
    private String day;
}
