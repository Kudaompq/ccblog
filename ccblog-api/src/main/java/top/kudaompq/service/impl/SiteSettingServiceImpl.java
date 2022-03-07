package top.kudaompq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.SiteSetting;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.SiteSettingMapper;
import top.kudaompq.model.vo.Badge;
import top.kudaompq.model.vo.Copyright;
import top.kudaompq.model.vo.Favorite;
import top.kudaompq.model.vo.Introduction;
import top.kudaompq.service.RedisService;
import top.kudaompq.service.SiteSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.utils.JacksonUtils;

import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class SiteSettingServiceImpl extends ServiceImpl<SiteSettingMapper, SiteSetting> implements SiteSettingService {

    @Autowired
    SiteSettingMapper siteSettingMapper;
    @Autowired
    RedisService redisService;

    @Override
    public Map<String, Object> getSiteInfo() {
        String redisKey = RedisKey.SITE_INTO_MAP;
        Map<String, Object> siteInfoMapFromRedis = redisService.getMapByValue(redisKey);
        if (siteInfoMapFromRedis != null){
            return siteInfoMapFromRedis;
        }
        List<SiteSetting> siteSettings = siteSettingMapper.selectList(null);
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> siteInfo = new HashMap<>();
        ArrayList<Badge> badges = new ArrayList<>();
        Introduction introduction = new Introduction();
        ArrayList<Favorite> favorites = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            if (s.getType() == 1){
                if ("copyright".equals(s.getNameEn())){
                    Copyright copyright = JacksonUtils.readValue(s.getValue(), Copyright.class);
                    siteInfo.put(s.getNameEn(),copyright);
                }else{
                    siteInfo.put(s.getNameEn(),s.getValue());
                }
            }else if (s.getType() == 2){
                Badge badge = JacksonUtils.readValue(s.getValue(), Badge.class);
                badges.add(badge);
            }else if(s.getType() == 3){
                if ("avatar".equals(s.getNameEn())) {
                    introduction.setAvatar(s.getValue());
                } else if ("name".equals(s.getNameEn())) {
                    introduction.setName(s.getValue());
                } else if ("github".equals(s.getNameEn())) {
                    introduction.setGithub(s.getValue());
                } else if ("qq".equals(s.getNameEn())) {
                    introduction.setQq(s.getValue());
                } else if ("bilibili".equals(s.getNameEn())) {
                    introduction.setBilibili(s.getValue());
                } else if ("netease".equals(s.getNameEn())) {
                    introduction.setNetease(s.getValue());
                } else if ("email".equals(s.getNameEn())) {
                    introduction.setEmail(s.getValue());
                } else if ("favorite".equals(s.getNameEn())) {
                    Favorite favorite = JacksonUtils.readValue(s.getValue(), Favorite.class);
                    favorites.add(favorite);
                } else if ("rollText".equals(s.getNameEn())) {
                    introduction.setRollText(s.getValue());
                }
            }
        }
        introduction.setFavorites(favorites);
        map.put("introduction",introduction);
        map.put("siteInfo",siteInfo);
        map.put("badges",badges);
        redisService.saveMapToValue(redisKey,map);
        return map;
    }

    @Override
    public Map<String, List<SiteSetting>> getList() {
        List<SiteSetting> siteSettings = siteSettingMapper.selectList(null);
        HashMap<String, List<SiteSetting>> map = new HashMap<>();
        ArrayList<SiteSetting> type1 = new ArrayList<>();
        ArrayList<SiteSetting> type2 = new ArrayList<>();
        ArrayList<SiteSetting> type3 = new ArrayList<>();
        for (SiteSetting siteSetting : siteSettings) {
            if (siteSetting.getType() == 1){
                type1.add(siteSetting);
            }else if (siteSetting.getType() == 2){
                type2.add(siteSetting);
            }else if (siteSetting.getType() == 3){
                type3.add(siteSetting);
            }
        }
        map.put("type1",type1);
        map.put("type2",type2);
        map.put("type3",type3);
        return map;
    }

    @Override
    public void updateSiteSettings(List<LinkedHashMap> siteSettings, List<Integer> deleteIds) {
        for (Integer id : deleteIds) {
            deleteSiteSettingById(id);
        }
        for (LinkedHashMap s : siteSettings) {
            SiteSetting siteSetting = JacksonUtils.convertValue(s, SiteSetting.class);
            if (siteSetting.getId() != null){
                updateSiteSetting(siteSetting);
            }else{
                saveSiteSetting(siteSetting);
            }
        }
        deleteSiteInfoRedisCache();
    }

    private void deleteSiteSettingById(Integer id){
        if (siteSettingMapper.deleteById(id) == 0) throw new PersistenceException("配置删除失败");
    }

    private void updateSiteSetting(SiteSetting siteSetting){
        if (siteSettingMapper.updateById(siteSetting) == 0) throw new PersistenceException("配置修改失败");
    }

    private void saveSiteSetting(SiteSetting siteSetting){
        if (siteSettingMapper.insert(siteSetting) == 0) throw new PersistenceException("配置添加失败");
    }

    /**
     * 删除缓存
     */
    private void deleteSiteInfoRedisCache(){
        redisService.deleteCacheByKey(RedisKey.SITE_INTO_MAP);
    }
}
