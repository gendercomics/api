package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MongoDbService {
/*
    @Value("${spring.data.mongodb.uri}")
    private String _mongoURI;

    public MongoTemplate getMongoTemplate() {
        MongoClientURI mongoClientURI = new MongoClientURI(_mongoURI);
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        return new MongoTemplate(mongoClient, "gendercomics-dev");
    }
*/
}
