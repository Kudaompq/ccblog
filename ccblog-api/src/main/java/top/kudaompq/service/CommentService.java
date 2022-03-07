package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import top.kudaompq.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import top.kudaompq.model.vo.PageComment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface CommentService extends IService<Comment> {

    int countByPageAndIsPublished(Integer page, Long blogId, Boolean isPublished);


    IPage<PageComment> getPageCommentListByPage(Integer page,Long blogId,Integer pageNum,Integer pageSize);

    IPage<Comment> getCommentListByPage(Integer page,Long blogId,Integer pageNum,Integer pageSize);

    void deleteCommentById(Long id);
}
