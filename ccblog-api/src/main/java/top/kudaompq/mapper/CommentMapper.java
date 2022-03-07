package top.kudaompq.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.model.vo.PageComment;

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
public interface CommentMapper extends BaseMapper<Comment> {

    int countByPageAndIsPublished(Integer page, Long blogId, Boolean isPublished);

    Page<PageComment> getParentCommentListByPage(Page<PageComment> commentPage,Integer page,Long blogId);

    List<PageComment> getPageCommentListByPageAndParentCommentId(Integer page, Long blogId, Long parentCommentId);

    Page<Comment> getParentCommentListByPageInAdmin(Page<Comment> commentPage,Integer page,Long blogId);

    List<Comment> getCommentListByParentCommentId(Integer page, Long blogId, Long parentCommentId);
}
