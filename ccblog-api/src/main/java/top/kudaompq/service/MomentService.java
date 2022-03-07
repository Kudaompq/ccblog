package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.models.auth.In;
import top.kudaompq.entity.Moment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface MomentService extends IService<Moment> {

    IPage<Moment> getMomentListByPage(Integer pageNum, Integer pageSize);

    void saveMoment(Moment moment);

    void deleteMoment(Integer id);

    void updateMoment(Moment moment);

    void addLikeById(Long id);
}
