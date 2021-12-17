package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.SearchService;
import net.gendercomics.api.model.Comic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Autowired
    private SearchService _searchService;

    @BeforeEach
    private void setup() {
        Comic comic = new Comic();
        comic.setId("comicId");
        comic.setTitle("hallo");
        comic.setSubTitle("welt");

        _mongoTemplate.insert(comic, "comics");
    }

    @AfterEach
    private void cleanup() {
        _mongoTemplate.remove(new Query(), "comics");
    }

    @Test
    public void whenSearch_ThenReturnComic_HavingTitle() {
        Comic comic = new Comic();
        comic.setId("comicId");
        comic.setTitle("hallo");
        comic.setSubTitle("welt");

        assertThat(_searchService.search("hallo").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);
    }

    @Test
    public void whenSearch_ThenReturnComic_HavingSubTitle() {
        Comic comic = new Comic();
        comic.setId("comicId");
        comic.setTitle("hallo");
        comic.setSubTitle("welt");

        assertThat(_searchService.search("welt").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);
    }

    @Test
    public void whenSearch_ThenReturnComic_HavingTitleIgnoreCase() {
        Comic comic = new Comic();
        comic.setId("comicId");
        comic.setTitle("hallo");
        comic.setSubTitle("welt");

        assertThat(_searchService.search("Hallo").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);
    }

    @Test
    public void whenSearch_PartialString_ThenReturnComic() {
        Comic comic = new Comic();
        comic.setId("comicId");
        comic.setTitle("hallo");
        comic.setSubTitle("welt");

        assertThat(_searchService.search("ha").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);

        assertThat(_searchService.search("elt").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);
    }

}
