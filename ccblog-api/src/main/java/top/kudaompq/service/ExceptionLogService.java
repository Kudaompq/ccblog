package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.ExceptionLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface ExceptionLogService extends IService<ExceptionLog> {

    void saveExceptionLog(ExceptionLog exceptionLog);

    void deleteExceptionLog(Long id);

    IPage<ExceptionLog> getLoginLogListByDate(String[] date, Integer pageNum, Integer pageSize);
}
