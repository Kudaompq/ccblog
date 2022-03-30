package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.vo.BlogInfo;
import top.kudaompq.model.vo.RandomBlog;
import top.kudaompq.model.vo.SearchBlog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface BlogService extends IService<Blog> {
    List<RandomBlog> getRandomBlogList();

    IPage getAllArticleInfosByPage(Integer pageNum, Integer pageSize, String title, Integer categoryId);

    boolean saveBlog(BlogDetail blogDetail);

    BlogDetail getBlogDetailById(Integer id);

    boolean deleteBlogById(Integer id);

    IPage<BlogInfo> getBlogInfoListByPage(Integer pageNum);

    BlogDetail getBlogById(Integer id);

    IPage<BlogInfo> getBlogInfoListPageByCategoryId(Integer pageNum,Integer id);

    IPage<BlogInfo> getBlogInfoListPageByTagId(Integer pageNum,Integer id);

    Map<String,Object> getArchiveBlog();

    List<SearchBlog> getSearchBlogListByQuery(String query);

    String getTitleById(Long id);

    List<Blog>  getIdAndTitleList();

    void updateViewsToRedis(Long id);

    void updateViews(Long id,Integer views);

}
