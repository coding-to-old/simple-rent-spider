package cto.github.rent.douban;

import cto.github.rent.douban.pipeline.DoubanPipeline;
import cto.github.rent.douban.template.DoubanArticleList;
import cto.github.rent.enums.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

/**
 * @Date 2019-09-24
 * @author ybbzbb
 * @Description 豆瓣爬虫启动类
 * */
@Component
public class DoubanSpiderTask implements Runnable {

    @Autowired
    private DoubanPipeline doubanPipeline;

    @Autowired
    private JedisPool jedisPool;

    private final static Site site = Site.me();

    private final static Class [] TEMPLATES = new Class[]{DoubanArticleList.class};

    private final static int THREAD_NUM = 10;


    static {
        site.setDomain(Platform.DOUBAN.getShorthand());
        site.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
    }

    @Override
    public void run() {

        //初始化 redis
        RedisPriorityScheduler redis = initRedisScheduler(site.toTask());

        OOSpider<?> ooSpider = OOSpider.create(site, doubanPipeline, TEMPLATES);

        ooSpider.thread(THREAD_NUM).setUUID(Platform.DOUBAN.getShorthand());
        ooSpider.setScheduler(redis).setExitWhenComplete(true);
        ooSpider.setIsExtractLinks(false).setEmptySleepTime(10);


        ProxyProvider proxyProvider = initProxy(site.toTask());

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(proxyProvider);

        ooSpider.setDownloader(httpClientDownloader);
        ooSpider.run();
    }

    public RedisPriorityScheduler initRedisScheduler(Task task) {
        RedisPriorityScheduler redis = new RedisPriorityScheduler(jedisPool);

        return redis;
    }

    public ProxyProvider initProxy(Task task) {
        return null;
    }

}