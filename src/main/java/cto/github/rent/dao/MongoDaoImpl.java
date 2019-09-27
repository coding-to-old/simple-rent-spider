package cto.github.rent.dao;

import com.mongodb.util.JSON;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description mongodb 操作
 * @Date 2019-09-25
 * @author ybbzbb
 */
@Component
public class MongoDaoImpl {

	Logger logger = LoggerFactory.getLogger(MongoDaoImpl.class);

	@Autowired
	private MongoClient mongoClient;

	/**
	 * 获取集合，如何集合不存在自动创建;
	 * 
	 * @author fcl
	 * @Param dbName
	 * @Param collectionName
	 * @Param indexJson {"name","1"}
	 * @return
	 */
	public MongoCollection<Document> getList(String dbName, String collectionName, String indexJson) {
		if (dbName == null || collectionName == null) {
			return null;
		}
		String clloname = collectionName.toLowerCase();
		MongoDatabase mdb = mongoClient.getDatabase(dbName);
		for (String p : mdb.listCollectionNames()) {
			if (p.toLowerCase().equals(clloname)) {
				return mdb.getCollection(clloname);
			}
		}
		MongoCollection<Document> coll = mdb.getCollection(clloname);
		if (indexJson == null) {
			String indexName = coll.createIndex(BsonDocument.parse(indexJson));
			logger.info("create mongodb index {} ", indexName);
		}

		return coll;
	}


	public void saveObject(String dbName, String collectionName,String jsonObject) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<DBObject> collection = database.getCollection(collectionName, DBObject.class);
		DBObject bson = (DBObject) JSON.parse(jsonObject);
		collection.insertOne(bson);
	}

	public void saveManyObject(String dbName, String collectionName, List<String> jsonArray) {
		MongoDatabase database = mongoClient.getDatabase(dbName);
		MongoCollection<DBObject> collection = database.getCollection(collectionName, DBObject.class);

		final List<DBObject> bsonArray = new ArrayList<>(jsonArray.size());

		jsonArray.stream().forEach( e -> {
			bsonArray.add((DBObject)JSON.parse(e));
		});

		collection.insertMany(bsonArray);
	}


	public List<String> selectAllCollName(String dbName){
		MongoDatabase database =mongoClient.getDatabase(dbName);
		MongoCursor<String> cursor=database.listCollectionNames().iterator();
		return Lists.newArrayList(cursor);
	}

	public void dropCollection(String collName) {
		MongoDatabase database =mongoClient.getDatabase(collName);
		 database.getCollection(collName).drop();
	}
	

}
