package top.kudaompq.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.kudaompq.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.vo.*;

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
public interface BlogMapper extends BaseMapper<Blog> {
    List<RandomBlog> getRandomBlogList(Integer limitNum);

    Page<BlogAdminInfo> getBlogInfosByTitleAndCategoryId(Page<BlogAdminInfo> page, String title, Integer categoryId);

    Page<BlogInfo> getBlogInfoListByPage(Page<BlogInfo> page);

    Page<BlogInfo> getBlogInfoListPageByCategoryId(Page<BlogInfo> page,Integer id);

    Page<BlogInfo> getBlogInfoListPageByTagId(Page<BlogInfo> page,Integer id);

    // 获得后台编辑的博客内容
    BlogDetail getBlogDetailById(Integer blogId);

    // 获得创建文章的所有月份
    List<String> getGroupYearMonthByIsPublished();

    List<ArchiveBlogVO> getArchiveBlogListByYearMonthAndIsPublished(String yearMonth);

    List<SearchBlog> getSearchBlogListByQuery(String query);

    String getTitleById(Long id);

    List<Blog> getIdAndTitleList();

    List<BlogView> getBlogViewList();

    List<CategoryBlogCount> getCategoryBlogCountList();


}
