package top.kudaompq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.model.vo.FriendVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Repository
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    List<FriendVO> getFriendVOList();

}
