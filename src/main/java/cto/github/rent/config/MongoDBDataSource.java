package cto.github.rent.config;


import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description mongodb db 连接
 * @Date 2019-09-25
 * @author ybbzbb
 */
@Configuration
public class MongoDBDataSource {

    @Value("${mongodb.uri}")
    private String mongoDBUri;

    @Value("${mongodb.userName}")
    private String userName;

    @Value("${mongodb.userPwd}")
    private String userPwd;

    @Value("${mongodb.connectDbName}")
    private String dbName;

    @Bean
    public MongoClient init(){

        final MongoClientOptions clientOptions = new MongoClientOptions.Builder()
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .readPreference(ReadPreference.secondaryPreferred())
                .connectionsPerHost(10)
                .socketTimeout(60000)
                .maxWaitTime(60000)
                .build();

        final List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        final MongoCredential credential = MongoCredential.createCredential(userName,
                dbName,
                userPwd.toCharArray());

        credentialsList.add(credential);

        List<ServerAddress> seeds = new ArrayList<>();

        String[] hostPorts = mongoDBUri.split(",");

        for(int i = 0 ; i < hostPorts.length;i++){
            String[] hps = hostPorts[i].split(":");
            ServerAddress serverAddress = new ServerAddress(hps[0], Integer.valueOf(hps[1]));
            seeds.add(serverAddress);
        }


        final MongoClient client = new MongoClient(seeds,credentialsList,clientOptions);

        return client;
    }
}
