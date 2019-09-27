package cto.github.rent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 豆瓣站点
 * @Date 2019-09-26
 * @author ybbzbb
 */
@Getter
@AllArgsConstructor
public enum DoubanSiteWeb {

    ARTICLE_LIST("https://www.douban.com/group/%s/discussion?start=0"),
    ARTICLE_DETAIL("https://www.douban.com/group/topic/%s/");

    private String url;

    public String getUrl(String value){
        return String.format(url, value);
    }


}
