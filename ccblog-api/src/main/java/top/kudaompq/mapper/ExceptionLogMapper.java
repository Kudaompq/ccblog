package top.kudaompq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.ExceptionLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

}
