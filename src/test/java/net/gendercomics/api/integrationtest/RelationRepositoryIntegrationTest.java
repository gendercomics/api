package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MongoTemplate _mongo;

    @Autowired
    private RelationRepository _relationRepository;

    @AfterEach
    private void cleanup() {
        _mongo.dropCollection("relations");
        _mongo.dropCollection("texts");
        _mongo.dropCollection("comics");
    }

    @Test
    public void whenFindSourceRelationByObjectId_ThenReturnRelation() {
        Relation relation = new Relation("comments", "aaaaaaaaaaaaaaaaaaaaaaaa", Text.class.getName(), "ffffffffffffffffffffffff", Comic.class.getName());
        relation = _relationRepository.insert(relation);

        List<Relation> relationList = _relationRepository.findRelationsBySourceId("aaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(relationList).isNotEmpty();
        assertThat(relationList.get(0).getSourceId()).isEqualTo("aaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test
    public void whenFindRelationsByTypeAndSourceObjectId_ThenReturnRelation() {
        Relation relation = new Relation("comments", "aaaaaaaaaaaaaaaaaaaaaaaa", Text.class.getName(), "ffffffffffffffffffffffff", Comic.class.getName());
        relation = _relationRepository.insert(relation);

        List<Relation> relationList = _relationRepository.findRelationsByTypeAndSourceObjectId("comments", "aaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(relationList).isNotEmpty();
        assertThat(relationList.get(0).getSourceId()).isEqualTo("aaaaaaaaaaaaaaaaaaaaaaaa");
    }
}