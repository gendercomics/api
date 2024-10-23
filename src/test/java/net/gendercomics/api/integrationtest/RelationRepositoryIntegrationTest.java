package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.repository.RelationRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class RelationRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate _mongo;

    @Autowired
    private RelationRepository _relationRepository;

    @AfterEach
    public void cleanup() {
        _mongo.dropCollection("relations");
        _mongo.dropCollection("texts");
        _mongo.dropCollection("comics");
    }

}