package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kudaompq.entity.Category;
import top.kudaompq.entity.CityVisitor;
import top.kudaompq.entity.Tag;
import top.kudaompq.entity.VisitRecord;
import top.kudaompq.mapper.*;
import top.kudaompq.model.vo.CategoryBlogCount;
import top.kudaompq.model.vo.TagBlogCount;
import top.kudaompq.service.DashboardService;
import top.kudaompq.service.VisitLogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 * @author: kudaompq
 * @date: 2022/2/14 12:28
 * @version: v1.0
 */

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    VisitLogMapper visitLogMapper;
    @Autowired
    VisitRecordMapper visitRecordMapper;
    @Autowired
    CityVisitorMapper cityVisitorMapper;

    private static final int visitRecordLimitNum = 30;


    @Override
    public int countVisitLogByToday() {
        return visitLogMapper.countVisitLogByToday();
    }

    @Override
    public int getBlogCount() {
        return blogMapper.selectCount(null);
    }

    @Override
    public int getCommentCount() {
        return commentMapper.selectCount(null);
    }

    @Override
    public Map<String, List> getCategoryBlogCountMap() {
        // 查询分类id对应的博客数量
        List<CategoryBlogCount> categoryBlogCountList = blogMapper.getCategoryBlogCountList();
        // 查询所有分类的id和名称
        List<Category> categorieList = categoryMapper.selectList(null);
        ArrayList<String> legend = new ArrayList<>();
        for (Category category : categorieList) {
            legend.add(category.getName());
        }
        // 分类对应的博客数量List
        List<CategoryBlogCount> series = new ArrayList<>();
        // 需要判断是否有空的category
        if (categoryBlogCountList.size() == categorieList.size()){
            HashMap<Long, String> m = new HashMap<>();
            for (Category category : categorieList) {
                m.put(category.getId(),category.getName());
            }
            for (CategoryBlogCount c : categoryBlogCountList) {
                c.setName(m.get(c.getId()));
                series.add(c);
            }
        }else{
            HashMap<Long, Integer> m = new HashMap<>();
            for (CategoryBlogCount c : categoryBlogCountList) {
                m.put(c.getId(),c.getValue());
            }
            for (Category c : categorieList) {
                CategoryBlogCount categoryBlogCount = new CategoryBlogCount();
                categoryBlogCount.setName(c.getName());
                Integer count = m.get(c.getId());
                if (count == null){
                    categoryBlogCount.setValue(0);
                }else{
                    categoryBlogCount.setValue(count);
                }
                series.add(categoryBlogCount);
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("legend",legend);
        map.put("series",series);
        return map;
    }

    @Override
    public Map<String, List> getTagBlogCountMap() {
        List<TagBlogCount> tagBlogCountList = tagMapper.getTagBlogCountList();
        List<Tag> tagList = tagMapper.selectList(null);
        ArrayList<String> legend = new ArrayList<>();
        for (Tag tag : tagList) {
            legend.add(tag.getName());
        }
        ArrayList<TagBlogCount> series = new ArrayList<>();
        if (tagBlogCountList.size() == tagList.size()){
            HashMap<Long, String> m = new HashMap<>();
            for (Tag tag : tagList) {
                m.put(tag.getId(),tag.getName());
            }
            for (TagBlogCount t : tagBlogCountList) {
                t.setName(m.get(t.getId()));
                series.add(t);
            }
        }else{
            HashMap<Long, Integer> m = new HashMap<>();
            for (TagBlogCount t : tagBlogCountList) {
                m.put(t.getId(), t.getValue());
            }
            for (Tag t : tagList) {
                TagBlogCount tagBlogCount = new TagBlogCount();
                tagBlogCount.setName(t.getName());
                Integer count = m.get(t.getId());
                if (count == null) {
                    tagBlogCount.setValue(0);
                } else {
                    tagBlogCount.setValue(count);
                }
                series.add(tagBlogCount);
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("legend",legend);
        map.put("series",series);
        return map;
    }

    @Override
    public Map<String, List> getVisitRecordMap() {
        List<VisitRecord> visitRecordList = visitRecordMapper.getVisitRecordByLimit(visitRecordLimitNum);
        ArrayList<String> date = new ArrayList<>(visitRecordList.size());
        ArrayList<Integer> pv = new ArrayList<>(visitRecordList.size());
        ArrayList<Integer> uv = new ArrayList<>(visitRecordList.size());
        for (int i = visitRecordList.size() - 1; i >= 0; i--) {
            VisitRecord visitRecord = visitRecordList.get(i);
            date.add(visitRecord.getDate());
            pv.add(visitRecord.getPv());
            uv.add(visitRecord.getUv());
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("date",date);
        map.put("pv",pv);
        map.put("uv",uv);
        return map;
    }

    @Override
    public List<CityVisitor> getCityVisitorList() {
        return cityVisitorMapper.selectList(new QueryWrapper<CityVisitor>().orderByDesc("uv"));
    }
}
