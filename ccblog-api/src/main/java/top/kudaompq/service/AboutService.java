package top.kudaompq.service;

import top.kudaompq.entity.About;
import com.baomidou.mybatisplus.extension.service.IService;
import top.kudaompq.model.vo.AboutVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface AboutService extends IService<About> {

    AboutVO getAbout();

    AboutVO getAboutToAdmin();

    void updateAbout(AboutVO about);

}
