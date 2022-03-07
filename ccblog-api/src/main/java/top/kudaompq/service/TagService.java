package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface TagService extends IService<Tag> {

    public IPage getTagListByPage(Integer pageNum, Integer pageSize);

    public void saveTag(Tag tag);

    public void updateTag(Tag tag);

    public void deleteTag(Integer id);

    public List<Tag> getTagList();

}
