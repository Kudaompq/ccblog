package top.kudaompq.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/1/30 14:28
 * @version: v1.0
 */

@Data
public class Introduction implements Serializable {

    private String avatar;
    private String name;
    private String github;
    private String qq;
    private String bilibili;
    private String netease;
    private String email;
    private String rollText;

    private List<Favorite> favorites = new ArrayList<>();

}
