package top.kudaompq.model.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/14 12:43
 * @version: v1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryBlogCount {
    private Long id;
    private String name;
    private Integer value;
}
