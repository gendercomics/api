package net.gendercomics.api.data.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoDbContainerTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate mongo;

    @Test
    void testConnection() {
        final String uuid = UUID.randomUUID().toString();
        this.mongo.createCollection(uuid);
        assertThat(this.mongo.collectionExists(uuid)).isTrue();
    }

}
