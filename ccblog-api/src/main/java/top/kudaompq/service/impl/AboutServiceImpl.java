package top.kudaompq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.kudaompq.common.RedisKey;
import top.kudaompq.entity.About;
import top.kudaompq.exception.PersistenceException;
import top.kudaompq.mapper.AboutMapper;
import top.kudaompq.model.vo.AboutVO;
import top.kudaompq.service.AboutService;
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
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

    @Autowired
    AboutMapper aboutMapper;

    @Autowired
    RedisService redisService;
    @Override
    public AboutVO getAbout() {
        String redisKey = RedisKey.ABOUT_INFO_OBJECT;
        AboutVO aboutFromRedis = redisService.getObjectByValue(redisKey, AboutVO.class);
        if (aboutFromRedis != null) return aboutFromRedis;
        List<About> abouts = aboutMapper.selectList(null);
        AboutVO about = new AboutVO();
        for (About a : abouts) {
            if (a.getNameEn().equals("title")){
                about.setTitle(a.getValue());
            }else if (a.getNameEn().equals("musicId")){
                about.setMusicId(a.getValue());
            }else if (a.getNameEn().equals("content")){
                about.setContent(MarkdownUtils.markdownToHtmlExtensions(a.getValue()));
            }else{
                about.setCommentEnabled(a.getValue().equals("true"));
            }
        }
        redisService.saveObjectToValue(redisKey,about);
        return about;
    }

    @Override
    public AboutVO getAboutToAdmin() {
        List<About> abouts = aboutMapper.selectList(null);
        AboutVO about = new AboutVO();
        for (About a : abouts) {
            if (a.getNameEn().equals("title")){
                about.setTitle(a.getValue());
            }else if (a.getNameEn().equals("musicId")){
                about.setMusicId(a.getValue());
            }else if (a.getNameEn().equals("content")){
                about.setContent(a.getValue());
            }else{
                about.setCommentEnabled(a.getValue().equals("true"));
            }
        }
        return about;
    }

    @Override
    public void updateAbout(AboutVO about) {
        aboutMapper.updateAbout("title",about.getTitle());
        aboutMapper.updateAbout("musicId",about.getMusicId());
        aboutMapper.updateAbout("content",about.getContent());
        aboutMapper.updateAbout("commentEnabled",""+about.getCommentEnabled());
        clearAboutCache();
    }

    private void updateAboutItem(String nameEn,String value){
        if (aboutMapper.updateAbout(nameEn,value) == 0) throw new PersistenceException("修改失败");
    }

    private void clearAboutCache(){
        redisService.deleteCacheByKey(RedisKey.ABOUT_INFO_OBJECT);
    }
}
