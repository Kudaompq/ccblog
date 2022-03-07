package top.kudaompq.service;

import top.kudaompq.entity.SiteSetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface SiteSettingService extends IService<SiteSetting> {

    Map<String,Object> getSiteInfo();

    Map<String, List<SiteSetting>> getList();

    void updateSiteSettings(List<LinkedHashMap> siteSettings,List<Integer> deleteIds);

}
