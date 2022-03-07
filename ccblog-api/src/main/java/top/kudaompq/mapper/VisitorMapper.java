package top.kudaompq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.Visitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.model.dto.VisitLogUUIDTime;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Mapper
@Repository
public interface VisitorMapper extends BaseMapper<Visitor> {

    int hasUUID(String uuid);

    int updatePVAndLastTimeByUUID(VisitLogUUIDTime dto);

    List<String> getNewVisitorIpSourceByYesterday();
}
