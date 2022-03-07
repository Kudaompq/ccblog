package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 徽章
 * @author: kudaompq
 * @date: 2022/1/30 14:32
 * @version: v1.0
 */

@Data
public class Badge implements Serializable {
    private String title;
    private String url;
    private String subject;
    private String value;
    private String color;
}
