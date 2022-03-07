package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.scheduling.annotation.Async;
import top.kudaompq.entity.Visitor;
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
public interface VisitorService extends IService<Visitor> {

    Boolean hasUUID(String uuid);

    @Async
    void saveVisitor(Visitor visitor);

    void updatePVAndLastTimeByUUID(VisitLogUUIDTime visitLogUUIDTime);

    List<String> getNewVisitorIpSourceByYesterday();

    IPage<Visitor> getVisitorListByDate(String[] date, Integer pageNum, Integer pageSize);

    void deleteVisitor(Long id,String uuid);
}
