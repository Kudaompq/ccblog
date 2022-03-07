package top.kudaompq.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/11 15:19
 * @version: v1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogUUIDTime {
    private String uuid;
    private Date createTime;
    private Integer pv;
}
