package top.kudaompq.common;

/**
 * @description: Redis Key
 * @author: kudaompq
 * @date: 2022/1/30 14:24
 * @version: v1.0
 */

public class RedisKey {

    /**
     * 站点信息key
     */
    public static final String SITE_INTO_MAP = "siteInfoMap";

    /**
     * 分类列表包括分类中包含博客个数Key
     */
    public static final String CATEGORY_COUNT_LIST = "categoryCountList";

    /**
     * 友链信息Key
     */
    public static final String FRIEND_INFO_MAP = "friendInfoMap";

    /**
     * 友链列表Key
     */
    public static final String FRIEND_LIST = "friendListMap";

    /**
     * AboutKey
     */
    public static final String ABOUT_INFO_OBJECT = "aboutInfo";

    /**
     * 时间轴Key
     */
    public static final String ARCHIVE_BLOG_MAP = "archiveBlogMap";

    /**
     * 访客标识Key
     */
    public static final String IDENTIFICATION_SET = "identificationSet";

    /**
     * 博客访问量KEY
     */
    public static final String BLOG_VIEWS_MAP = "blogViewsMap";

    /**
     * 标签云KEY
     */
    public static final String TAG_CLOUD_LIST = "tagCloudList";
}
