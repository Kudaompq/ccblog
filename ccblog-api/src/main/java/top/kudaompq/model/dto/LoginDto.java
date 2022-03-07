package top.kudaompq.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description: 登录信息传递类
 * @author: kudaompq
 * @date: 2022/1/4 16:41
 * @version: v1.0
 */

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
