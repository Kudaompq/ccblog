package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/30 14:30
 * @version: v1.0
 */

@Data
public class Favorite  implements Serializable {
    private String title;
    private String content;

}
