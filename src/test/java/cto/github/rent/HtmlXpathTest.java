package cto.github.rent;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selector;
import us.codecraft.webmagic.selector.XpathSelector;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Description html xpth 获取
 * @Date 2019-09-25
 * @author ybbzbb
 */
public class HtmlXpathTest {

    public static void main(String[] args) {
        String html = "<tr class='title' ><a href='http://whatever.com/aaa'></a></tr>";

        String html1 = "<div class='title' ><a href='http://whatever.com/aaa'></a></div><div><a href='http://whatever.com/bbb'></a></div>";

//
//        Selector selector = new XpathSelector("//td");
//
//        String select = selector.select(html);
//
//        System.out.println(select);

        Html selectable = new Html(html);
        Selectable links = selectable.xpath("//tr[@class=\"title\"]/a/@href");

        System.out.println(links.get());
    }
}
