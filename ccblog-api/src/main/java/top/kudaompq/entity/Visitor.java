package top.kudaompq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访客标识码
     */
    private String uuid;

    /**
     * ip
     */
    private String ip;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 首次访问时间
     */
    private LocalDateTime createTime;

    /**
     * 最后访问时间
     */
    private LocalDateTime lastTime;

    /**
     * 访问页数统计
     */
    private Integer pv;

    /**
     * user-agent用户代理
     */
    private String userAgent;

    public Visitor(String uuid, String ip, String userAgent) {
        this.uuid = uuid;
        this.ip = ip;
        this.createTime = LocalDateTime.now();
        this.lastTime = LocalDateTime.now();
        this.pv = 0;
        this.userAgent = userAgent;
    }
}
