package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Publisher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

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

    @Test
    public void whenSaveComic_WithPublisherOverride_ThenComicHasPublisherOverrides() {
        Comic comic = new Comic();
        comic.setId("comicId-override");
        comic.setPublishers(new ArrayList<>());
        comic.getPublishers().add(new Publisher());
        comic.getPublishers().get(0).setId("publisherId");
        comic.getPublishers().get(0).setLocationOverride("alternative location");

        Comic savedComic = _comicService.save(comic, "integration-test");

        assertThat(savedComic.getPublisherOverrides())
                .isNotNull()
                .hasSize(1)
                .containsEntry("publisherId", "alternative location");
    }

    @Test
    public void whenSaveComic_HavingPublisherOverride_AndPublisherOverrideRemoved_ThenComicPublisherOverridesIsNull() {
        Comic comic = new Comic();
        comic.setId("comicId-override");
        comic.setPublishers(new ArrayList<>());
        comic.getPublishers().add(new Publisher());
        comic.getPublishers().get(0).setId("publisherId");
        comic.getPublishers().get(0).setLocationOverride("alternative location");

        Comic savedComic = _comicService.save(comic, "integration-test");

        assertThat(savedComic.getPublisherOverrides())
                .isNotNull()
                .hasSize(1)
                .containsEntry("publisherId", "alternative location");

        savedComic.getPublishers().get(0).setLocationOverride(null);
        Comic savedComicWithoutOverride = _comicService.save(savedComic, "integration-test");

        assertThat(savedComicWithoutOverride.getPublisherOverrides())
                .isNull();
    }

    @Test
    public void whenSaveComic_HavingPublisherOverride_AndPublisherOverrideChanged_ThenComicPublisherOverridesIsUpdated() {
        Comic comic = new Comic();
        comic.setId("comicId-override");
        comic.setPublishers(new ArrayList<>());
        comic.getPublishers().add(new Publisher());
        comic.getPublishers().get(0).setId("publisherId");
        comic.getPublishers().get(0).setLocationOverride("alternative location");

        Comic savedComic = _comicService.save(comic, "integration-test");

        assertThat(savedComic.getPublisherOverrides())
                .isNotNull()
                .hasSize(1)
                .containsEntry("publisherId", "alternative location");

        savedComic.getPublishers().get(0).setLocationOverride("new location");
        Comic savedComicWithoutOverride = _comicService.save(savedComic, "integration-test");

        assertThat(savedComic.getPublisherOverrides())
                .isNotNull()
                .hasSize(1)
                .containsEntry("publisherId", "new location");
    }

}
