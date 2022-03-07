package top.kudaompq.enums;

/**
 * @description: 用户访问行为枚举类
 * @author: kudaompq
 * @date: 2022/1/30 13:42
 * @version: v1.0
 */

public enum VisitBehavior {

    UNKNOWN("UNKNOWN", "UNKNOWN"),

    INDEX("访问页面", "首页"),
    ARCHIVE("访问页面", "归档"),
    MOMENT("访问页面", "动态"),
    FRIEND("访问页面", "友链"),
    ABOUT("访问页面", "关于我"),

    BLOG("查看博客", ""),
    CATEGORY("查看分类", ""),
    TAG("查看标签", ""),
    SEARCH("搜索博客", ""),
    CLICK_FRIEND("点击友链", ""),
    LIKE_MOMENT("点赞动态", ""),
    ;

    /**
     * 访问行为
     */
    private String behavior;

    /**
     * 访问内容
     */
    private String content;

    VisitBehavior(String behavior, String content) {
        this.behavior = behavior;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getBehavior() {
        return behavior;
    }
}
