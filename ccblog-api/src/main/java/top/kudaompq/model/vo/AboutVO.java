package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: AboutVO
 * @author: kudaompq
 * @date: 2022/2/2 16:44
 * @version: v1.0
 */

@Data
public class AboutVO implements Serializable {

    private String title;
    private String musicId;
    private String content;
    private Boolean commentEnabled;
}
