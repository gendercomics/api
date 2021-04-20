package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.RelationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RelationService.class)
public class RelationServiceTest {

    @Autowired
    private RelationService _relationService;

    @MockBean
    private RelationRepository _relationRepository;

    @Test
    public void whenFindAllRelations_thenReturnList() {
        String comicId = "comicId";

        List<Relation> relations = new ArrayList<>();
        // sourceRelation
        relations.add(new Relation());
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.get(0).setSource(sourceComic);
        // targetRelation
        relations.add(new Relation());
        Comic targetComic = new Comic();
        targetComic.setId(comicId);
        relations.get(1).setTarget(new Comic());

        when(_relationService.findAllRelations(comicId)).thenReturn(relations);

        List<Relation> queriedRelations = _relationService.findAllRelations(comicId);
        assertNotNull(queriedRelations);
        assertEquals(2, queriedRelations.size());
    }

    @Test
    public void whenFindAllRelationsGroupedByType_thenReturnMap() {
        String comicId = "comicId";

        List<Relation> relations = new ArrayList<>();
        // sourceRelation
        relations.add(new Relation());
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.get(0).setSource(sourceComic);
        relations.get(0).setRelationType(RelationType.comments);

        // targetRelation 1
        relations.add(new Relation());
        Comic targetComic = new Comic();
        targetComic.setId(comicId);
        relations.get(1).setTarget(targetComic);
        relations.get(1).setRelationType(RelationType.comments);
        // targetRelation 2
        relations.add(new Relation());
        Comic targetComic2 = new Comic();
        targetComic.setId(comicId);
        relations.get(2).setTarget(targetComic2);
        relations.get(2).setRelationType(RelationType.comments);

        when(_relationService.findAllRelations(comicId)).thenReturn(relations);

        Map<RelationType, List<Relation>> map = _relationService.findAllRelationsGroupedByType(comicId);
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(3, map.get(RelationType.comments).size());
    }

}