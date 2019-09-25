package cto.github.rent.douban.pipeline;

import com.alibaba.fastjson.JSON;
import cto.github.rent.dao.MongoDaoImpl;
import cto.github.rent.douban.template.DoubanGroupContentList;
import cto.github.rent.util.DateFormUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import java.util.ArrayList;
import java.util.Date;
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
        String collectionName = task.getUUID() + "keyName" + DateFormUtils.getNowDay();

        List<String> data = new ArrayList<>(60);

        if (o instanceof DoubanGroupContentList) {
            collectionName = collectionName.replace("keyName", "list");
            final DoubanGroupContentList list = (DoubanGroupContentList)o;

            list.getDetails().stream().forEach( e -> {
                final String jsonStr = JSON.toJSONString(o);
                data.add(jsonStr);
            });
        }
//
//        mongoDao.saveObject(dbName, collectionName, jsonStr);

        log.info("save data:" + data);
    }
}
