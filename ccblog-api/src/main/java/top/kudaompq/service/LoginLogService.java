package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.scheduling.annotation.Async;
import top.kudaompq.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface LoginLogService extends IService<LoginLog> {

    @Async
    void saveLoginLog(LoginLog log);

    void deleteLoginLog(Long id);

    IPage<LoginLog> getLoginLogListByDate(String[] date,Integer pageNum,Integer pageSize);
}
