package top.kudaompq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.kudaompq.entity.CityVisitor;
import top.kudaompq.mapper.CityVisitorMapper;
import top.kudaompq.service.CityVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class CityVisitorServiceImpl extends ServiceImpl<CityVisitorMapper, CityVisitor> implements CityVisitorService {

    @Autowired
    CityVisitorMapper cityVisitorMapper;

    @Override
    public void saveCityVisitor(CityVisitor cityVisitor) {
        cityVisitorMapper.saveCityVisitor(cityVisitor);
    }
}
