package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.scheduling.annotation.Async;
import top.kudaompq.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface OperationLogService extends IService<OperationLog> {

    @Async
    void saveOperationLog(OperationLog log);

    IPage<OperationLog> getOperationLogListByDate(String[] date, Integer pageNum, Integer pageSize);

    void deleteOperationLog(Long id);
}
