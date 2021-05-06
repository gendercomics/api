package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ComicServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ComicService _comicService;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @BeforeEach
    private void setup() {
        Text text = new Text();
        text.setId("textId");
        text.setMetaData(new MetaData());
        text.getMetaData().setCreatedOn(new Date());
        text.getMetaData().setCreatedBy("integration_test_user");
        _mongoTemplate.createCollection(Text.class);
        _mongoTemplate.insert(text, "texts");

        Comic comic = new Comic();
        comic.setId("comicId");
        _mongoTemplate.createCollection(Comic.class);
        _mongoTemplate.insert(comic, "comics");

        Relation relation = new Relation("comments", text.getId(), text.getClass().getName(), comic.getId(), comic.getClass().getName());
        _mongoTemplate.createCollection(Relation.class);
        _mongoTemplate.insert(relation, "relations");
    }

    @AfterEach
    private void cleanup() {
        _mongoTemplate.dropCollection(Comic.class);
        _mongoTemplate.dropCollection(Relation.class);
        _mongoTemplate.dropCollection(Text.class);
    }

    @Test
    public void whenSaveNewComment_ThenTextExists_AndRelationExists() {
        String comicId = "comicId";
        Text text = new Text();

        _comicService.saveComment(comicId, text, "integration_test_user");

        assertThat(_mongoTemplate.findAll(Text.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        assertThat(_mongoTemplate.findAll(Relation.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void whenSaveExistingComment_ThenExistingTextUpdated_AndRelationUnchanged() {
        String comicId = "comicId";

        Text text = _mongoTemplate.findAll(Text.class).get(0);
        text.setValue(text.getValue() + "updated");

        Relation relation = _mongoTemplate.findAll(Relation.class).get(0);

        _comicService.saveComment(comicId, text, "integration_test_user");

        assertThat(_mongoTemplate.findAll(Text.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(_mongoTemplate.findAll(Relation.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Relation relationAfterUpdate = _mongoTemplate.findAll(Relation.class).get(0);
        assertThat(relationAfterUpdate)
                .isEqualTo(relation);
    }
}
