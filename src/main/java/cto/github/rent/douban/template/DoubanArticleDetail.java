package cto.github.rent.douban.template;

import cto.github.rent.enums.DoubanSite;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 豆瓣帖子详情页面
 * @Date 2019-09-26
 * @author ybbzbb
 */
@TargetUrl({"http[s]{0,1}://www.douban.com/group/topic/*/"})
@Getter
@Setter
@Log4j
public class DoubanArticleDetail implements AfterExtractor {

    private String site = DoubanSite.CN.toString();

    @ExtractBy("//div[@id=\"content\"]/h1[1]/text()")
    private String pageTitle;

    @ExtractBy("//head/title/text()")
    private String articleTitle;

    @ExtractBy("//div[@class=\"user-face\"]/a/@href")
    private String authorId;

    @ExtractBy("//div[@class=\"user-face\"]/a/img/@alt")
    private String authorName;

    @ExtractBy("//div[@class=\"user-face\"]/a/img/@src")
    private String authorAvatar;

    @ExtractBy("//div[@class=\"topic-doc\"]/span[@class=\"color-green\"]/text()")
    private String articleCreateTime;

    @ExtractBy("//div[@id=\"link-report\"]//img/@src")
    private List<String> articleImage;

    @ExtractBy("//div[@id=\"link-report\"]/allText()")
    private String articleContent;

    @ExtractByUrl
    private String pageUrl;

    private String articleId;

    private Date fetchTime = new Date();


    @Override
    public void afterProcess(Page page) {
        authorId = Optional.ofNullable(this.authorId).orElse("").replaceAll("[^\\d]","");
        authorId = Optional.ofNullable(this.pageUrl).orElse("").replaceAll("[^\\d]","");

    }
}
