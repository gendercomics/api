package net.gendercomics.api.data.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MongoDbService {

    @Value("${spring.data.mongodb.uri}")
    private String _mongoURI;

    public MongoTemplate getMongoTemplate() {
        MongoClient mongoClient = MongoClients.create(_mongoURI);
        return new MongoTemplate(mongoClient, "gendercomics-dev");
    }

    public MongoDatabase getDb(String dbName) {
        MongoClient mongoClient = MongoClients.create(_mongoURI);
        return mongoClient.getDatabase(dbName);
    }
}
