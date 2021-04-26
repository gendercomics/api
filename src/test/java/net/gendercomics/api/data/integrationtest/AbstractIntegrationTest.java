package net.gendercomics.api.data.integrationtest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class AbstractIntegrationTest {

    @Container
    private static final MongoDbContainer MONGO_DB_CONTAINER = new MongoDbContainer(DockerImageName.parse("mongo:4.2.13"));

    @DynamicPropertySource
    private static void mongoDBProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getUri);
    }

}
