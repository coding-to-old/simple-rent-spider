package cto.github.rent.douban.pipeline;

import com.alibaba.fastjson.JSON;
import cto.github.rent.dao.MongoDaoImpl;
import cto.github.rent.douban.template.DoubanArticleDetail;
import cto.github.rent.douban.template.DoubanArticleList;
import cto.github.rent.util.DateFormUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 持久化豆瓣数据
 * @Date 2019-09-25
 * @author ybbzbb
 */
@Log4j
@Component
public class DoubanPipeline implements PageModelPipeline<Object> {

    @Value("${mongodb.dbName}")
    private String dbName;

    @Autowired
    private MongoDaoImpl mongoDao;

    @Override
    public void process(Object o, Task task) {

        log.info("amazon pipeline process:" + o.getClass().getName() + " task:" + task.getUUID() + "  ");
        String collectionName = task.getUUID() + "_keyName" + DateFormUtils.getNowDay();

        List<String> data = new ArrayList<>(60);

        if (o instanceof DoubanArticleList) {
            collectionName = collectionName.replace("keyName", "list");
            final DoubanArticleList list = (DoubanArticleList)o;

            list.getDetails().stream().forEach( e -> {
                final String jsonStr = JSON.toJSONString(e);
                data.add(jsonStr);
            });
        }else if (o instanceof DoubanArticleDetail){
            collectionName = collectionName.replace("keyName", "detail");
            data.add(JSON.toJSONString(o));
        }

        mongoDao.saveManyObject(dbName, collectionName, data);

        log.info("save data:" + data);
    }
}
