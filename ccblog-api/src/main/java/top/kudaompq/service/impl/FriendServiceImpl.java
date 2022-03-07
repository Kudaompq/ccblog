package top.kudaompq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.Friend;
import top.kudaompq.entity.SiteSetting;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.FriendMapper;
import top.kudaompq.mapper.SiteSettingMapper;
import top.kudaompq.model.vo.FriendInfo;
import top.kudaompq.model.vo.FriendVO;
import top.kudaompq.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.kudaompq.service.RedisService;
import top.kudaompq.utils.markdown.MarkdownUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    RedisService redisService;

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    SiteSettingMapper siteSettingMapper;

    @Override
    public FriendInfo getFriendInfo() {
        String redisKey = RedisKey.FRIEND_INFO_MAP;
        FriendInfo friendInfoFromRedis = redisService.getObjectByValue(redisKey, FriendInfo.class);
        if (friendInfoFromRedis != null) return friendInfoFromRedis;
        List<SiteSetting> siteSettings = siteSettingMapper.selectList(new QueryWrapper<SiteSetting>().eq("type", 4));
        FriendInfo friendInfo = new FriendInfo();
        for (SiteSetting siteSetting : siteSettings) {
            if ("friendContent".equals(siteSetting.getNameEn())) {
                friendInfo.setContent(MarkdownUtils.markdownToHtmlExtensions(siteSetting.getValue()));
            } else if ("friendCommentEnabled".equals(siteSetting.getNameEn())) {
                if ("1".equals(siteSetting.getValue())) {
                    friendInfo.setCommentEnabled(true);
                } else {
                    friendInfo.setCommentEnabled(false);
                }
            }
        }
        redisService.saveObjectToValue(redisKey,friendInfo);
        return friendInfo;
    }

    @Override
    public List<FriendVO> getFriendList() {
        String redisKey = RedisKey.FRIEND_LIST;
        List<FriendVO> friendListFromRedis = redisService.getListByValue(redisKey);
        if (friendListFromRedis != null){
            return friendListFromRedis;
        }
        List<FriendVO> friendVOList = friendMapper.getFriendVOList();
        redisService.saveListToValue(redisKey,friendVOList);
        return friendVOList;
    }

    @Override
    public IPage<Friend> getFriendListByPage(Integer pageNum, Integer pageSize) {
        Page<Friend> page = new Page<>(pageNum, pageSize);
        return friendMapper.selectPage(page,new QueryWrapper<Friend>().orderByAsc("create_time"));
    }

    @Override
    public void updateFriendInfoCommentEnabled(Boolean commentEnabled) {
        if (siteSettingMapper.updateFriendInfoCommentEnabled(commentEnabled) == 0) throw new PersistenceException("修改友链评论状态失败");
        removeFriendInfoCache();
    }

    @Override
    public void updateFriendInfoContent(String content) {
        if (siteSettingMapper.updateFriendInfoContent(content) == 0) throw new PersistenceException("修改友链页面信息失败");
        removeFriendInfoCache();
    }

    private void removeFriendInfoCache(){
        redisService.deleteCacheByKey(RedisKey.FRIEND_INFO_MAP);
    }

    private void removeFriendListCache(){
        redisService.deleteCacheByKey(RedisKey.FRIEND_LIST);
    }


}
