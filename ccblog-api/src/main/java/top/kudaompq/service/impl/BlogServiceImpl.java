package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.common.FinalCode;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.Blog;
import top.kudaompq.entity.BlogTag;
import top.kudaompq.entity.Comment;
import top.kudaompq.exception.NotFoundException;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.BlogMapper;
import top.kudaompq.mapper.BlogTagMapper;
import top.kudaompq.mapper.CommentMapper;
import top.kudaompq.mapper.TagMapper;
import top.kudaompq.model.dto.BlogDetail;
import top.kudaompq.model.vo.*;
import top.kudaompq.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.service.RedisService;
import top.kudaompq.utils.markdown.MarkdownUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    BlogMapper blogMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogTagMapper blogTagMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    RedisService redisService;

    private static final int randomBlogLimitNum = 5;

    /**
     * 项目启动的时候，保存所有的博客浏览量到Redis
     */
    @PostConstruct
    private void saveBlogViewsToRedis(){
        String redisKey = RedisKey.BLOG_VIEWS_MAP;
        if (!redisService.hasKey(redisKey)){
            Map<Long, Integer> map = getBlogViewsMap();
            redisService.saveMapToHash(redisKey,map);
        }
    }

    private Map<Long,Integer> getBlogViewsMap(){
        List<BlogView> blogViewList = blogMapper.getBlogViewList();
        HashMap<Long, Integer> map = new HashMap<>();
        for (BlogView blogView : blogViewList) {
            map.put(blogView.getId(),blogView.getViews());
        }
        return map;
    }

    @Override
    public List<RandomBlog> getRandomBlogList() {
        return blogMapper.getRandomBlogList(randomBlogLimitNum);
    }

    @Override
    public IPage getAllArticleInfosByPage(Integer pageNum, Integer pageSize, String title, Integer categoryId) {

        Page<BlogAdminInfo> page = new Page<>(pageNum,pageSize);

        return blogMapper.getBlogInfosByTitleAndCategoryId(page,title,categoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBlog(BlogDetail blogDetail) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDetail,blog);
        if (blog.getId() != null){
            blog.setUpdateTime(LocalDateTime.now());
            blog.setCategoryId( ((Integer) blogDetail.getCategoryId()).longValue());
            int update = blogMapper.updateById(blog);
            if (update > 0){
                 blogTagMapper.delete(new QueryWrapper<BlogTag>().eq("blog_id", blog.getId()));
                List<Object> tagList = blogDetail.getTagList();
                for (Object tagId : tagList) {
                    BlogTag blogTag = new BlogTag();
                    blogTag.setBlogId(blog.getId());
                    blogTag.setTagId(((Integer)tagId).longValue());
                    int insert = blogTagMapper.insert(blogTag);
                    if (insert == 0) return false;
                }
            }
            return true;
        }
        blog.setViews(0);
        blog.setCreateTime(LocalDateTime.now());
        blog.setUpdateTime(LocalDateTime.now());
        blog.setCategoryId(((Integer) blogDetail.getCategoryId()).longValue());
        int insert = blogMapper.insert(blog);
        if (insert > 0){
            List<Object> tagList = blogDetail.getTagList();
            for (Object tagId : tagList) {
                BlogTag blogTag = new BlogTag();
                blogTag.setBlogId(blog.getId());
                blogTag.setTagId(((Integer)tagId).longValue());
                int insert1 = blogTagMapper.insert(blogTag);
                if (insert1 == 0) return false;
            }
            redisService.saveKVToHash(RedisKey.BLOG_VIEWS_MAP,blog.getId(),0);
            redisService.deleteCacheByKey(RedisKey.ARCHIVE_BLOG_MAP);
            redisService.deleteCacheByKey(RedisKey.CATEGORY_COUNT_LIST);
            return true;
        }
        return false;
    }

    @Override
    public BlogDetail getBlogDetailById(Integer id) {
        return blogMapper.getBlogDetailById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBlogById(Integer id) {
        int i = blogMapper.deleteById(id);
        if (i > 0){
           blogTagMapper.delete(new QueryWrapper<BlogTag>().eq("blog_id", id));
           commentMapper.delete(new QueryWrapper<Comment>().eq("blog_id",id));
           redisService.deleteCacheByKey(RedisKey.CATEGORY_COUNT_LIST);
            redisService.deleteCacheByKey(RedisKey.ARCHIVE_BLOG_MAP);
            return true;
        }
        return false;
    }

    @Override
    public IPage<BlogInfo> getBlogInfoListByPage(Integer pageNum) {
        Page<BlogInfo> page = new Page<>(pageNum, FinalCode.pageSize);
        blogMapper.getBlogInfoListByPage(page);
        List<BlogInfo> records = page.getRecords();
        for (BlogInfo record : records) {
            record.setViews((Integer) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP,record.getId()));
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public BlogDetail getBlogById(Integer id) {
        BlogDetail blog = blogMapper.getBlogDetailById(id);
        if (blog == null){
            throw new NotFoundException("该文章不存在");
        }
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        int views = (int) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP, id.longValue());
        blog.setViews(views);
        return blog;
    }

    @Override
    public IPage<BlogInfo> getBlogInfoListPageByCategoryId(Integer pageNum,Integer id) {
        Page<BlogInfo> page = new Page<>(pageNum,FinalCode.pageSize);
        page = blogMapper.getBlogInfoListPageByCategoryId(page, id);
        List<BlogInfo> records = page.getRecords();
        for (BlogInfo record : records) {
            record.setViews((Integer) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP,record.getId()));
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public IPage<BlogInfo> getBlogInfoListPageByTagId(Integer pageNum, Integer id) {
        Page<BlogInfo> page = new Page<>(pageNum,FinalCode.pageSize);
        blogMapper.getBlogInfoListPageByTagId(page,id);
        List<BlogInfo> records = page.getRecords();
        for (BlogInfo record : records) {
            record.setViews((Integer) redisService.getValueByHashKey(RedisKey.BLOG_VIEWS_MAP,record.getId()));
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public Map<String, Object> getArchiveBlog() {
        String redisKey = RedisKey.ARCHIVE_BLOG_MAP;
        Map<String, Object> mapFromRedis = redisService.getMapByValue(redisKey);
        if (mapFromRedis != null) return mapFromRedis;
        HashMap<String, Object> map = new HashMap<>();
        List<String> groupYearMonths = blogMapper.getGroupYearMonthByIsPublished();
        LinkedHashMap<String, List<ArchiveBlogVO>> archiveBlogMap = new LinkedHashMap<>();
        for (String s : groupYearMonths) {
            List<ArchiveBlogVO> archiveBlogList = blogMapper.getArchiveBlogListByYearMonthAndIsPublished(s);
            archiveBlogMap.put(s,archiveBlogList);
        }
        Integer count = blogMapper.selectCount(new QueryWrapper<Blog>().eq("is_published", 1));
        map.put("blogMap",archiveBlogMap);
        map.put("count",count);
        redisService.saveMapToValue(redisKey,map);
        return map;
    }

    @Override
    public List<SearchBlog> getSearchBlogListByQuery(String query) {
        List<SearchBlog> blogList = blogMapper.getSearchBlogListByQuery(query);
        for (SearchBlog searchBlog : blogList) {
            String content = searchBlog.getContent();
            int contentLength = content.length();
            int index = content.indexOf(query) - 10;
            index = Math.max(index, 0);
            int end = index + 21;
            end = Math.min(end,contentLength -1);
            searchBlog.setContent(content.substring(index,end));
        }
        return blogList;
    }

    @Override
    public String getTitleById(Long id) {
        return blogMapper.getTitleById(id);
    }

    @Override
    public List<Blog> getIdAndTitleList() {
        return blogMapper.getIdAndTitleList();
    }

    @Override
    public void updateViewsToRedis(Long id) {
        redisService.incrementByHashKey(RedisKey.BLOG_VIEWS_MAP,id,1);
    }

    @Override
    public void updateViews(Long id, Integer views) {
        Blog blog = new Blog();
        blog.setId(id);
        blog.setViews(views);
        if (blogMapper.updateById(blog) == 0) throw new PersistenceException("更新博客views失败");
    }
}
