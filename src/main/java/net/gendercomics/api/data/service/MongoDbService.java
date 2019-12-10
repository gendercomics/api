package net.gendercomics.api.data.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MongoDbService {

    public MongoTemplate getMongoTemplate() {
        MongoCredential credential = MongoCredential.createScramSha1Credential("gendercomics_api", "admin", new String("test123").toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"), credential, options);

        return new MongoTemplate(mongoClient, "gendercomics-dev");
    }

}
