package top.kudaompq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/14 16:19
 * @version: v1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagBlogCount {

    private Long id;
    private String name;
    private Integer value;
}
