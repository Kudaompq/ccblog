package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.Blog;
import top.kudaompq.entity.Category;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.BlogMapper;
import top.kudaompq.mapper.CategoryMapper;
import top.kudaompq.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.service.RedisService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    RedisService redisService;


    @Override
    public List<Category> getCategoryListAndCount() {
        String redisKey = RedisKey.CATEGORY_COUNT_LIST;
        List<Category> categoryListFromRedis = redisService.getListByValue(redisKey);
        if (categoryListFromRedis != null){
            return categoryListFromRedis;
        }
        List<Category> categories = categoryMapper.selectList(null);
        for (Category category : categories) {
            category.setCount(blogMapper.selectCount(new QueryWrapper<Blog>().eq("category_id",category.getId())));
        }
        redisService.saveListToValue(redisKey,categories);
        return categories;
    }

    @Override
    public void saveCategory(Category category) {
        if (categoryMapper.selectOne(new QueryWrapper<Category>().eq("category_name",category.getName())) != null)
            throw new PersistenceException("该分类已存在！");
        if (categoryMapper.insert(category) != 1)  throw new PersistenceException("保存分类失败！");
        redisService.deleteCacheByKey(RedisKey.CATEGORY_COUNT_LIST);
    }

    @Override
    public IPage getCategoryListByPage(Integer pageNum, Integer pageSize) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        return categoryMapper.selectPage(page, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Integer id) {
        if  (blogMapper.selectCount(new QueryWrapper<Blog>().eq("category_id", id))  != 0){
            throw new PersistenceException("请确保该分类下不存在文章！");
        }
        if  (categoryMapper.deleteById(id) != 1) throw new PersistenceException("删除分类失败！");
        redisService.deleteCacheByKey(RedisKey.CATEGORY_COUNT_LIST);

    }

    @Override
    public void updateCategory(Category category) {
        if (categoryMapper.selectOne(new QueryWrapper<Category>().eq("category_name",category.getName())) != null)
            throw new PersistenceException("该分类已存在！");
        if (categoryMapper.updateById(category) != 1) throw new PersistenceException("更新分类失败！");
        redisService.deleteCacheByKey(RedisKey.CATEGORY_COUNT_LIST);
    }
}
