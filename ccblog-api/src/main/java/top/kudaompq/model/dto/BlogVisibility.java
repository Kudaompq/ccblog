package top.kudaompq.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 博客可见化DTO
 * @author: kudaompq
 * @date: 2022/2/1 14:58
 * @version: v1.0
 */

@Data
public class BlogVisibility  implements Serializable {

    private Boolean appreciation;
    private Boolean recommend;
    private Boolean commentEnabled;
    private Boolean top;
    private Boolean published;
}
