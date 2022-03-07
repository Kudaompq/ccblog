package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.VisitLog;
import com.baomidou.mybatisplus.extension.service.IService;
import top.kudaompq.model.dto.VisitLogUUIDTime;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface VisitLogService extends IService<VisitLog> {

    void saveVisitLog(VisitLog log);

    List<VisitLogUUIDTime> getUUIDAndCreateTimeByYesterday();

    IPage<VisitLog> getVisitLogListByDate(String[] date,String uuid, Integer pageNum, Integer pageSize);

    void deleteVisitLog(Long id);
}
