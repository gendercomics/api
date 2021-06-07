package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.RelationService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private RelationService _relationService;

    @Autowired
    private MongoTemplate _mongoTemplate;

    @BeforeEach
    private void insertRelation() {
        Text text = new Text();
        text.setId("textId");
        _mongoTemplate.createCollection(Text.class);
        _mongoTemplate.insert(text, "texts");

        Comic comic = new Comic();
        comic.setId("comicId");
        _mongoTemplate.createCollection(Comic.class);
        _mongoTemplate.insert(comic, "comics");

        Relation relation = new Relation("comments", text.getId(), text.getClass().getName(), comic.getId(), comic.getClass().getName());
        relation.setMetaData(new MetaData());
        relation.getMetaData().setCreatedOn(new Date());
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
    public void whenFindAllRelationsForTypeComments_withComicId_ThenReturnTextObject() {
        Text text = new Text();
        text.setId("textId");

        List<Object> relations = _relationService.findAllRelationsForType("comments", "comicId");
        assertThat(relations)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(relations.get(0))
                .isNotNull()
                .isInstanceOf(Text.class)
                .isEqualTo(text);
    }

    @Test
    public void whenFindAllRelationsForTypeComments_withTextId_ThenReturnComicObject() {
        Comic comic = new Comic();
        comic.setId("comicId");

        List<Object> relations = _relationService.findAllRelationsForType("comments", "textId");
        assertThat(relations)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(relations.get(0))
                .isNotNull()
                .isInstanceOf(Comic.class)
                .isEqualTo(comic);
    }

    @Test
    public void whenDeleteBySourceId_ThenRelationDeleted() {
        List<Relation> relations = _mongoTemplate.findAll(Relation.class);
        assertThat(relations).isNotEmpty();

        _relationService.deleteBySourceId("textId");

        relations = _mongoTemplate.findAll(Relation.class);
        assertThat(relations).isEmpty();
    }

    @Test
    public void whenRelationExists_ThenReturnTrue() {
        assertThat(_relationService.relationExists("textId", "comicId")).isTrue();
    }

    @Test
    public void whenRelationNotExists_ThenReturnFalse() {
        assertThat(_relationService.relationExists("textId", "nonExistingId")).isFalse();
    }

    @Test
    public void whenSaveNewRelation_ThenRelationExists() {
        Relation relation = _relationService.save(new Relation("comments", "newTextId", Text.class.getName(), "targetId", Comic.class.getName()), "integration_test_user");

        assertThat(relation).isNotNull();
        assertThat(relation.getId()).isNotNull();

        assertThat(_mongoTemplate.findAll(Relation.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void whenSaveExistingRelation_ThenExistingRelationUpdated() {
        Relation existingRelation = _mongoTemplate.findAll(Relation.class).get(0);

        Relation relationAfterUpdate = _relationService.save(new Relation("comments", "textId", Text.class.getName(), "comicId", Comic.class.getName()), "integration_test_user");

        assertThat(_mongoTemplate.findAll(Relation.class))
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(relationAfterUpdate.getMetaData().getChangedBy()).isEqualTo("integration_test_user");

        assertThat(relationAfterUpdate.getId()).isEqualTo(existingRelation.getId());
    }
}
