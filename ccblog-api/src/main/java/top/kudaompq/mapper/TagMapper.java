package top.kudaompq.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.model.vo.TagBlogCount;

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
public interface TagMapper extends BaseMapper<Tag> {

    List<TagBlogCount> getTagBlogCountList();

}
