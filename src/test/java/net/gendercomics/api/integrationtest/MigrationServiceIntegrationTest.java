package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.data.service.TextService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MigrationResult;
import net.gendercomics.api.model.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MigrationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MigrationService _migrationService;

    @Autowired
    private TextService _textService;

    @Autowired
    private ComicService _comicService;

    @Autowired
    private MongoTemplate _mongo;

    @Test
    public void whenComicCommentToRelation_ThenOk() {
        Text text = new Text();
        text.setValue("hello gendercomics");
        text = _textService.save(text, "integrationtest");

        Comic comic = new Comic();
        comic.setComments(new ArrayList<>());
        comic.getComments().add(text);
        comic = _comicService.save(comic, "integrationtest");

        MigrationResult result = _migrationService.comicCommentToRelation();
        assertEquals("OK", result.getStatus());
        assertNotNull(result.getSource());
        assertEquals(1, result.getSource().size());
        assertNotNull(result.getResult());
        assertEquals(1, result.getResult().size());

        Comic resultComic = _comicService.getComic(comic.getId());
       // assertThat(resultComic.getTextCommentsComic().get(0).getValue()).isEqualTo("hello gendercomics");
    }

    @AfterEach
    private void cleanup() {
        _mongo.dropCollection("comics");
        _mongo.dropCollection("relations");
        _mongo.dropCollection("texts");
    }
}