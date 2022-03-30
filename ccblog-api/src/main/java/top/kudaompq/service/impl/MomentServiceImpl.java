package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import top.kudaompq.entity.Moment;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.MomentMapper;
import top.kudaompq.service.MomentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.utils.markdown.MarkdownUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class MomentServiceImpl extends ServiceImpl<MomentMapper, Moment> implements MomentService {

    @Autowired
    MomentMapper momentMapper;

    @Override
    public IPage<Moment> getMomentListByPage(Integer pageNum, Integer pageSize) {
        Page<Moment> momentPage = new Page<>(pageNum, pageSize);
        Page<Moment> res = momentMapper.selectPage(momentPage, new QueryWrapper<Moment>().eq("is_published",true).orderByDesc("create_time"));
        ArrayList<Moment> moments = new ArrayList<>();
        for (Moment record : res.getRecords()) {
            record.setContent(MarkdownUtils.markdownToHtmlExtensions(record.getContent()));
            moments.add(record);
        }
        res.setRecords(moments);
        return res;
    }

    @Override
    public void saveMoment(Moment moment) {
        moment.setCreateTime(LocalDateTime.now());
        moment.setLikes(0);
        if (momentMapper.insert(moment) != 1){
            throw new PersistenceException("动态添加失败");
        }

    }

    @Override
    public void deleteMoment(Integer id) {
        if (momentMapper.deleteById(id) != 1){
            throw new PersistenceException("删除失败");
        }
    }

    @Override
    public void updateMoment(Moment moment) {
        if (momentMapper.updateById(moment) != 1){
            throw new PersistenceException("动态修改失败");
        }
    }

    @Override
    public void addLikeById(Long id) {
       if (momentMapper.addLikeById(id) != 1){
           throw new PersistenceException("点赞失败");
       }
    }
}
