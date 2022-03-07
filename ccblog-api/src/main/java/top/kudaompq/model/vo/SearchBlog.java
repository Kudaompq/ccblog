package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/4 14:11
 * @version: v1.0
 */

@Data
public class SearchBlog  implements Serializable {

    private Long id;
    private String title;
    private String content;
}
