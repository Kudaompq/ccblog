package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.entity.VisitLog;
import top.kudaompq.entity.Visitor;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.VisitLogMapper;
import top.kudaompq.model.dto.VisitLogUUIDTime;
import top.kudaompq.service.VisitLogService;
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
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements VisitLogService {

    @Autowired
    VisitLogMapper visitLogMapper;

    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    public void saveVisitLog(VisitLog log) {
        log.setIpSource(IpAddressUtils.getCityInfo(log.getIp()));
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setOs(os);
        log.setBrowser(browser);
        if (visitLogMapper.insert(log) == 0) throw new PersistenceException("日志添加失败");
    }

    @Override
    public List<VisitLogUUIDTime> getUUIDAndCreateTimeByYesterday() {
        return visitLogMapper.getUUIDAndCreateTimeByYesterday();
    }

    @Override
    public IPage<VisitLog> getVisitLogListByDate(String[] date,String uuid, Integer pageNum, Integer pageSize) {
        Page<VisitLog> page = new Page<>(pageNum, pageSize);
        if (uuid == null || "".equals(uuid)){
            if (date.length == 2){
                return visitLogMapper.selectPage(page,new QueryWrapper<VisitLog>().orderByDesc("create_time").between("create_time",date[0],date[1]));
            }else {
                return visitLogMapper.selectPage(page,new QueryWrapper<VisitLog>().orderByDesc("create_time"));
            }
        }else{
            if (date.length == 2){
                return visitLogMapper.selectPage(page,new QueryWrapper<VisitLog>().orderByDesc("create_time")
                        .between("create_time",date[0],date[1])
                        .like("uuid",uuid));
            }else {
                return visitLogMapper.selectPage(page,new QueryWrapper<VisitLog>().orderByDesc("create_time") .like("uuid",uuid));
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteVisitLog(Long id) {
        if (visitLogMapper.deleteById(id) == 0) throw new PersistenceException("删除日志失败");
    }
}
