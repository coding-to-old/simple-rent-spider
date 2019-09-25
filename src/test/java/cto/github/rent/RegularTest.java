package cto.github.rent;

/**
 * @Description 正则测试类
 * @Date 2019-09-25
 * @author ybbzbb
 */
public class RegularTest {

    public static void main(String[] args) {

        String url1 = "https://www.douban.com/group/topic/147575575/";

        System.out.println( url1.replaceAll("[^\\d]","") );


    }
}
