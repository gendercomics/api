package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Relation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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
        relations.add(new Relation("comments"));
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.get(0).setSource(sourceComic);
        // targetRelation
        relations.add(new Relation("comments"));
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
        relations.add(new Relation("comments"));
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.get(0).setSource(sourceComic);

        // targetRelation 1
        relations.add(new Relation("comments"));
        Comic targetComic = new Comic();
        targetComic.setId(comicId);
        relations.get(1).setTarget(targetComic);
        // targetRelation 2
        relations.add(new Relation("comments"));
        Comic targetComic2 = new Comic();
        targetComic.setId(comicId);
        relations.get(2).setTarget(targetComic2);

        when(_relationService.findAllRelations(comicId)).thenReturn(relations);

        Map<String, List<Relation>> map = _relationService.findAllRelationsGroupedByType(comicId);
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(3, map.get("comments").size());
    }

}