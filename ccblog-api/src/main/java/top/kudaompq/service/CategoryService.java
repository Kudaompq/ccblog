package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.Category;
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
public interface CategoryService extends IService<Category> {

    List<Category> getCategoryListAndCount();

    void saveCategory(Category category);

    IPage getCategoryListByPage(Integer pageNum, Integer pageSize);

    void deleteCategory(Integer id);

    void updateCategory(Category category);

}
