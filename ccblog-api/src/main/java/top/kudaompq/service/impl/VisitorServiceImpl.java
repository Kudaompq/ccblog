package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.Visitor;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.VisitorMapper;
import top.kudaompq.model.dto.VisitLogUUIDTime;
import top.kudaompq.service.RedisService;
import top.kudaompq.service.VisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.UserAgentUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    @Autowired
    UserAgentUtils userAgentUtils;

    @Autowired
    RedisService redisService;

    @Override
    public Boolean hasUUID(String uuid) {
        return visitorMapper.hasUUID(uuid) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVisitor(Visitor visitor) {
        String ipSource = IpAddressUtils.getCityInfo(visitor.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(visitor.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        visitor.setIpSource(ipSource);
        visitor.setOs(os);
        visitor.setBrowser(browser);
        if (visitorMapper.insert(visitor) == 0) throw new PersistenceException("访客添加失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePVAndLastTimeByUUID(VisitLogUUIDTime dto) {
        visitorMapper.updatePVAndLastTimeByUUID(dto);
    }

    @Override
    public List<String> getNewVisitorIpSourceByYesterday() {
        return visitorMapper.getNewVisitorIpSourceByYesterday();
    }

    @Override
    public IPage<Visitor> getVisitorListByDate(String[] date, Integer pageNum, Integer pageSize) {
        Page<Visitor> page = new Page<>(pageNum, pageSize);
        if (date.length == 2){
            return visitorMapper.selectPage(page,new QueryWrapper<Visitor>().orderByDesc("last_time").between("last_time",date[0],date[1]));
        }else{
            return visitorMapper.selectPage(page,new QueryWrapper<Visitor>().orderByDesc("last_time"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVisitor(Long id,String uuid) {
        redisService.deleteValueBySet(RedisKey.IDENTIFICATION_SET,uuid);
        if (visitorMapper.delete(new QueryWrapper<Visitor>().eq("uuid",uuid).eq("id",id)) == 0) throw new PersistenceException("删除访客失败");
    }


}
