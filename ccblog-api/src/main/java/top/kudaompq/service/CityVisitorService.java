package top.kudaompq.service;

import top.kudaompq.entity.CityVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface CityVisitorService extends IService<CityVisitor> {

    void saveCityVisitor(CityVisitor cityVisitor);
}
