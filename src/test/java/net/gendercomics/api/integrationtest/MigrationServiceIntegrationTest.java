package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.data.service.TextService;
import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MigrationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MigrationService _migrationService;

    @Autowired
    private TextService _textService;

    @Autowired
    private ComicServiceImpl _comicService;

    @Autowired
    private MongoTemplate _mongo;

    @AfterEach
    private void cleanup() {
        _mongo.dropCollection("comics");
        _mongo.dropCollection("relations");
        _mongo.dropCollection("texts");
    }
}