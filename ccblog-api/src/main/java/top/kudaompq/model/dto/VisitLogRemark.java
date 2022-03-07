package top.kudaompq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description: 访问日志备注
 * @author: kudaompq
 * @date: 2022/2/7 13:02
 * @version: v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogRemark implements Serializable {

    /**
     * 访问内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;


}
