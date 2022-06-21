package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate _mongo;

    @Autowired
    private RelationRepository _relationRepository;

    @AfterEach
    private void cleanup() {
        _mongo.dropCollection("relations");
        _mongo.dropCollection("texts");
        _mongo.dropCollection("comics");
    }

}