package top.kudaompq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.kudaompq.entity.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import top.kudaompq.model.vo.FriendInfo;
import top.kudaompq.model.vo.FriendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kudaompq
 * @since 2022-01-29
 */
public interface FriendService extends IService<Friend> {

    FriendInfo getFriendInfo();

    List<FriendVO> getFriendList();

    IPage<Friend> getFriendListByPage(Integer pageNum, Integer pageSize);

    void updateFriendInfoCommentEnabled(Boolean commentEnabled);

    void updateFriendInfoContent(String content);


}
