package top.kudaompq.utils;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @description: 通过qq获取头像和名称
 * @author: kudaompq
 * @date: 2022/2/10 13:43
 * @version: v1.0
 */

public class QQInfoUtils {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final String QQ_NICKNAME_URL = "https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins={1}";
    private static final String QQ_AVATAR_URL = "https://q.qlogo.cn/g?b=qq&nk=%s&s=100";

    /**
    * @Description: 获得qq昵称
    * @Param: [qq]
    * @return: java.lang.String
    * @Author: Kudaompq
    * @Date: 2022/2/10
    */
    public static String getQQNickname(String qq) throws UnsupportedEncodingException {
        java.lang.String res = restTemplate.getForObject(QQ_NICKNAME_URL, java.lang.String.class, qq);
        byte[] bytes = res.getBytes("iso-8859-1");
        java.lang.String nickname = new java.lang.String(bytes, "gb18030").split(",")[6].replace("\"", "");
        if ("".equals(nickname)) {
            return "nickname";
        }
        return nickname;
    }

    public static ImageUtils.ImageResource getImageResourceByQQ(String qq){
        return ImageUtils.getImageByRequest(String.format(QQ_AVATAR_URL,qq));
    }

    public static String getQQAvatarURLByServerUpload(String qq) throws IOException {
        return ImageUtils.saveImage(getImageResourceByQQ(qq));
    }

    public static String getQqAvatarURLByGithubUpload(String qq){
        return ImageUtils.pushGithub(getImageResourceByQQ(qq));
    }

    public static boolean isQQNumber(String nickname){
        return nickname.matches("^[1-9][0-9]{4,10}$");
    }

}
