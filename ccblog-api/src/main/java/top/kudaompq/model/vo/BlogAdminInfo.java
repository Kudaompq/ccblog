package top.kudaompq.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.kudaompq.entity.Category;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/31 23:01
 * @version: v1.0
 */

@Data
public class BlogAdminInfo  implements Serializable {
    private Integer id;

    private String title;

    private Boolean published;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;


    private Boolean commentEnabled;

    private Boolean appreciation;

    private Boolean recommend;

    private Boolean top;

    private Category category;
}
