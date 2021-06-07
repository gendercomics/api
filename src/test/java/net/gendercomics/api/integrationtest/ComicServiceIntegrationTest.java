package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class ComicServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ComicService _comicService;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @BeforeEach
    private void setup() {
        Comic comic = new Comic();
        comic.setId("comicId");
        _mongoTemplate.createCollection(Comic.class);
        _mongoTemplate.insert(comic, "comics");
    }

    @AfterEach
    private void cleanup() {
        _mongoTemplate.dropCollection(Comic.class);
    }

    @Test
    public void whenFindComicById_ThenReturnComic() {
        assertThat(_comicService.getComic("comicId"))
                .isNotNull();
    }

}
