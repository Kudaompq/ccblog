package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.entity.OperationLog;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.OperationLogMapper;
import top.kudaompq.service.OperationLogService;
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
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Autowired
    OperationLogMapper operationLogMapper;

    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    public void saveOperationLog(OperationLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setIpSource(ipSource);
        log.setOs(os);
        log.setBrowser(browser);
        if  (operationLogMapper.insert(log) != 1){
            throw new PersistenceException("日志添加错误");
        }

    }

    @Override
    public IPage<OperationLog> getOperationLogListByDate(String[] date, Integer pageNum, Integer pageSize) {
        Page<OperationLog> page = new Page<>(pageNum, pageSize);
        if (date.length == 2){
            return operationLogMapper.selectPage(page, new QueryWrapper<OperationLog>().between("create_time", date[0], date[1]).orderByDesc("create_time"));
        }else{
            return operationLogMapper.selectPage(page,new QueryWrapper<OperationLog>().orderByDesc("create_time"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOperationLog(Long id) {
        if (operationLogMapper.deleteById(id) == 0) throw new PersistenceException("删除日志失败");
    }
}
