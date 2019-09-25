package cto.github.rent.douban.template;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.utils.DateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.xsoup.w3c.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 豆瓣组内容列表页面
 * @Date 2019-09-25
 * @author ybbzbb
 */
@TargetUrl({"http[s]{0,1}://www.douban.com/group/*/discussion*"})
@Getter
@Setter
@Log4j
public class DoubanGroupContentList implements AfterExtractor {

    private String site;

    @ExtractByUrl
    private String sourceUrl;

    private List<DoubanGroupDetail> details;

    @Override
    public void afterProcess(Page page) {

        List<Selectable> nodes = page.getHtml().xpath("//div[@id=\"wrapper\"]//tr").nodes();

        final Date fetchTime = new Date();

        details = new ArrayList<>(60);

        nodes.stream().forEach( e -> {

            final String detailUrl = e.xpath("//td[@class=\"title\"]/a/@href").get();

            if (StringUtil.isBlank(detailUrl)) {
                return;
            }

            final String detailId = Optional.ofNullable(detailUrl).orElse("").replaceAll("[^\\d]","");

            final String detailName = e.xpath("//td[@class=\"title\"]/a/@title").get();

            final String author = e.xpath("//td[2]/a/text()").get();

            String authorId = e.xpath("//td[1]/a/@href").get();
            authorId = Optional.ofNullable(authorId).orElse("").replaceAll("[^\\d]","");

            final String commentCount = e.xpath("//td[2]/text()").get();

            final String lastCommentTime = e.xpath("//td[@class=\"time\"]/text()").get();

            final DoubanGroupDetail detail = DoubanGroupDetail.builder()
                    .site("CN")
                    .sourceUrl(sourceUrl)
                    .detailId(detailId)
                    .detailUrl(detailUrl)
                    .detailName(detailName)
                    .author(author)
                    .authorId(authorId)
                    .commentCount(commentCount)
                    .lastCommentTime(lastCommentTime)
                    .fetchTime(fetchTime)
                    .build();

            details.add(detail);

//        page.addTargetRequest(new Request(detailUrl));

        });


    }

    @Builder
    @Getter
    @Setter
    public static class DoubanGroupDetail{

        private String site;

        private String sourceUrl;

        private String detailId;

        private String detailUrl;

        private String detailName;

        private String author;

        private String authorId;

        private String commentCount;

        private String lastCommentTime;

        private Date fetchTime;
    }
}
