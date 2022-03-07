package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.kudaompq.entity.LoginLog;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.LoginLogMapper;
import top.kudaompq.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.utils.IpAddressUtils;
import top.kudaompq.utils.UserAgentUtils;

import java.time.LocalDateTime;
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
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    LoginLogMapper loginLogMapper;

    @Autowired
    UserAgentUtils userAgentUtils;

    @Override
    @Transactional
    public void saveLoginLog(LoginLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setIpSource(ipSource);
        log.setOs(os);
        log.setBrowser(browser);
        log.setCreateTime(LocalDateTime.now());
        if (loginLogMapper.insert(log) == 0) throw new PersistenceException("日志添加失败");
    }

    @Override
    public void deleteLoginLog(Long id) {
        if (loginLogMapper.deleteById(id) == 0) throw new PersistenceException("日志删除失败");
    }

    @Override
    public IPage<LoginLog> getLoginLogListByDate(String[] date, Integer pageNum, Integer pageSize) {
        Page<LoginLog> page = new Page<>(pageNum, pageSize);
        if (date.length == 2){
            return loginLogMapper.selectPage(page,new QueryWrapper<LoginLog>().orderByDesc("create_time").between("create_time",date[0],date[1]));
        }else{
            return loginLogMapper.selectPage(page,new QueryWrapper<LoginLog>().orderByDesc("create_time"));

        }

    }
}
