package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.HyperLink;
import net.gendercomics.api.model.MigrationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MigrationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MigrationService _migrationService;

    @Autowired
    private ComicServiceImpl _comicService;

    @Autowired
    private MongoTemplate _mongo;

    @BeforeEach
    void setup() {
        _mongo.dropCollection(Comic.class);
        _mongo.createCollection(Comic.class);
    }

    @AfterEach
    void cleanup() {
        _mongo.dropCollection(Comic.class);
    }

    @Test
    public void whenListEmptyHyperlinks_withNoComics_thenResultIsOk() {
        MigrationResult result = _migrationService.listEmptyHyperlink();

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(MigrationResult.OK);
    }

    @Test
    public void whenListEmptyHyperlinks_withComicHavingNullUrlHyperlink_thenComicListed() {
        Comic comic = new Comic();
        comic.setId("comic-with-empty-link");
        comic.setTitle("Test Comic");
        HyperLink emptyLink = new HyperLink();
        comic.setHyperLinks(List.of(emptyLink));
        _mongo.insert(comic, "comics");

        MigrationResult result = _migrationService.listEmptyHyperlink();

        assertThat(result.getStatus()).isEqualTo(MigrationResult.OK);
        assertThat(result.getSource()).isNotNull().isNotEmpty();
    }

    @Test
    public void whenListEmptyHyperlinks_withComicHavingValidHyperlink_thenComicNotListed() {
        Comic comic = new Comic();
        comic.setId("comic-with-valid-link");
        comic.setTitle("Valid Comic");
        HyperLink validLink = new HyperLink();
        validLink.setUrl("https://example.com");
        comic.setHyperLinks(List.of(validLink));
        _mongo.insert(comic, "comics");

        MigrationResult result = _migrationService.listEmptyHyperlink();

        assertThat(result.getStatus()).isEqualTo(MigrationResult.OK);
        assertThat(result.getSource()).isNotNull().isEmpty();
    }

    @Test
    public void whenRemoveEmptyHyperlinks_thenHyperlinksCleared() {
        Comic comic = new Comic();
        comic.setId("comic-to-fix");
        comic.setTitle("Comic To Fix");
        HyperLink emptyLink = new HyperLink();
        comic.setHyperLinks(List.of(emptyLink));
        _mongo.insert(comic, "comics");

        MigrationResult result = _migrationService.removeEmptyHyperlink();

        assertThat(result.getStatus()).isEqualTo(MigrationResult.OK);
        assertThat(result.getResult()).isNotNull();
    }
}