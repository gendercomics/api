package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import net.gendercomics.api.model.Publisher;
import net.gendercomics.api.model.Series;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class ComicServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ComicServiceImpl _comicService;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @BeforeEach
    private void setup() {
        Comic comic = new Comic();
        comic.setId("comicId");
        _mongoTemplate.dropCollection(Comic.class); // why ist this needed?
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

    @Test
    public void whenDeleteComic_ThenNotFound() {
        _comicService.delete("comicId");
        assertThat(_comicService.getComic("comicId")).isNull();
    }

    @Test
    public void whenDeleteSeries_ThenDeleteEntriesInComics() {
        // data preparation
        Comic publishingSeries = new Comic();
        publishingSeries.setId("publishing-series-id");
        publishingSeries.setType(ComicType.publishing_series);
        publishingSeries = _comicService.save(publishingSeries, "integration-test");

        assertThat(publishingSeries.getId()).isNotNull();

        Comic comicSeries = new Comic();
        comicSeries.setId("comic-series-id");
        comicSeries.setType(ComicType.comic_series);
        comicSeries = _comicService.save(comicSeries, "integration-test");

        assertThat(comicSeries.getId()).isNotNull();

        Comic comicInSeries = new Comic();
        comicInSeries.setId("comicInSeries-Id");
        comicInSeries.setSeriesList(new ArrayList<>());
        comicInSeries.getSeriesList().add(new Series());
        comicInSeries.getSeriesList().get(0).setComic(publishingSeries);

        comicInSeries.getSeriesList().add(new Series());
        comicInSeries.getSeriesList().get(1).setComic(comicSeries);

        comicInSeries = _comicService.save(comicInSeries, "integration-test");

        assertThat(comicInSeries.getId()).isNotNull();
        assertThat(comicInSeries.getSeriesList().get(0).getComic().getId()).isEqualTo("publishing-series-id");
        assertThat(comicInSeries.getSeriesList().get(1).getComic().getId()).isEqualTo("comic-series-id");

        // execute
        _comicService.delete("publishing-series-id");

        assertThat(_comicService.getComic("publishing-series-id")).isNull();
        assertThat(_comicService.getComic("comic-series-id")).isNotNull();

        assertThat(_comicService.getComic("comicInSeries-Id")).isNotNull();
        assertThat(_comicService.getComic("comicInSeries-Id").getSeriesList())
                .isNotEmpty()
                .hasSize(1);
        assertThat(_comicService.getComic("comicInSeries-Id").getSeriesList().get(0).getComicId()).isEqualTo("comic-series-id");
    }

}
