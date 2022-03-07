package top.kudaompq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/11 14:19
 * @version: v1.0
 */

@Data
@AllArgsConstructor
public class BlogView implements Serializable {
    private Long id;
    private Integer views;
}
