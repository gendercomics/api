package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.impl.SearchServiceImpl;
import net.gendercomics.api.model.Comic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate _mongoTemplate;

    @Autowired
    private SearchServiceImpl _searchServiceImpl;

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

        assertThat(_searchServiceImpl.search("hallo").getComics())
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

        assertThat(_searchServiceImpl.search("welt").getComics())
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

        assertThat(_searchServiceImpl.search("Hallo").getComics())
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

        assertThat(_searchServiceImpl.search("ha").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);

        assertThat(_searchServiceImpl.search("elt").getComics())
                .isNotNull()
                .hasSize(1)
                .contains(comic);
    }

}
