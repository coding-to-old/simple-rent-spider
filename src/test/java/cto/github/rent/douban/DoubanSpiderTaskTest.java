package cto.github.rent.douban;

import cto.github.rent.RentSpiderApplicationTests;
import cto.github.rent.enums.Platform;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;


@RunWith(SpringRunner.class)
public class DoubanSpiderTaskTest extends RentSpiderApplicationTests {

    @Autowired
    private DoubanSpiderTask doubanSpiderTask;

    @Autowired
    private JedisPool jedisPool;

    private final static Site site = Site.me();

    static {
        site.setDomain(Platform.DOUBAN.getShorthand());
    }

    @Test
    public void runTest(){
        RedisPriorityScheduler redis = new RedisPriorityScheduler(jedisPool);

//        redis.push(new Request("https://www.douban.com/group/609270/discussion?start=0"),site.toTask());
        redis.push(new Request("https://www.douban.com/group/topic/153548695/"),site.toTask());

        doubanSpiderTask.run();
    }

}
