package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.BlogTag;
import top.kudaompq.entity.Tag;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.BlogTagMapper;
import top.kudaompq.mapper.TagMapper;
import top.kudaompq.service.RedisService;
import top.kudaompq.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogTagMapper blogTagMapper;

    @Autowired
    RedisService redisService;

    @Override
    public IPage getTagListByPage(Integer pageNum, Integer pageSize) {
        Page<Tag> page = new Page<>(pageNum,pageSize);
        return tagMapper.selectPage(page, null);
    }

    @Override
    public void saveTag(Tag tag) {
        if (tagMapper.selectOne(new QueryWrapper<Tag>().eq("tag_name", tag.getName())) != null)
            throw new PersistenceException("该标签已存在，请勿重复创建！");
        if (tagMapper.insert(tag) != 1) throw new PersistenceException("保存标签失败！");
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);
    }

    @Override
    public void updateTag(Tag tag) {
        if (tagMapper.selectOne(new QueryWrapper<Tag>().eq("tag_name", tag.getName())) != null)
            throw new PersistenceException("该标签已存在，请勿重复创建！");
        if (tagMapper.updateById(tag) != 1) throw new PersistenceException("修改标签失败！");
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Integer id) {
        if (blogTagMapper.selectCount(new QueryWrapper<BlogTag>().eq("tag_id",id)) != 0)
            throw new PersistenceException("该标签下存在文章，请确保标签下无文章再进行删除！");
        if (tagMapper.deleteById(id) != 1) throw new PersistenceException("删除标签失败！");
        redisService.deleteCacheByKey(RedisKey.TAG_CLOUD_LIST);

    }

    @Override
    public List<Tag> getTagList() {
        String redisKey = RedisKey.TAG_CLOUD_LIST;
        List<Tag> tagListFromRedis = redisService.getListByValue(redisKey);
        if (tagListFromRedis != null){
            return tagListFromRedis;
        }
        List<Tag> tags = tagMapper.selectList(null);
        redisService.saveListToValue(RedisKey.TAG_CLOUD_LIST,tags);
        return tags;
    }

}
