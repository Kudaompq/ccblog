package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.entity.ExceptionLog;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.ExceptionLogMapper;
import top.kudaompq.service.ExceptionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.UserAgentUtils;

import java.time.LocalDateTime;
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
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    @Autowired
    ExceptionLogMapper exceptionLogMapper;

    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExceptionLog(ExceptionLog exceptionLog) {
        String ipSource = IpAddressUtils.getCityInfo(exceptionLog.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(exceptionLog.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        exceptionLog.setCreateTime(LocalDateTime.now());
        exceptionLog.setIpSource(ipSource);
        exceptionLog.setOs(os);
        exceptionLog.setBrowser(browser);
        if (exceptionLogMapper.insert(exceptionLog) == 0) throw new PersistenceException("日志添加失败");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExceptionLog(Long id) {
        if (exceptionLogMapper.deleteById(id) == 0) throw new PersistenceException("日志删除失败");
    }

    @Override
    public IPage<ExceptionLog> getLoginLogListByDate(String[] date, Integer pageNum, Integer pageSize) {
        Page<ExceptionLog> page = new Page<>(pageNum, pageSize);
        if (date.length == 2){
            return exceptionLogMapper.selectPage(page,new QueryWrapper<ExceptionLog>().orderByDesc("create_time").between("create_time",date[0],date[1]));
        }else{
            return exceptionLogMapper.selectPage(page,new QueryWrapper<ExceptionLog>().orderByDesc("create_time"));

        }
    }
}
