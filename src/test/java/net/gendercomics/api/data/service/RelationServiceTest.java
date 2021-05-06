package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.data.repository.TextRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.Text;
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

    @MockBean
    private TextRepository _textRepository;

    @MockBean
    private ComicRepository _comicRepository;

    @Test
    public void whenFindAllRelations_thenReturnList() {
        String comicId = "comicId";

        List<Relation> relations = new ArrayList<>();
        // sourceRelation
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.add(new Relation("comments", "sourceId1", sourceComic.getClass().getName(), "targetId1", Text.class.getName()));
        // targetRelation
        Comic targetComic = new Comic();
        targetComic.setId(comicId);
        relations.add(new Relation("comments", "sourceId1", Text.class.getName(), "targetId2", targetComic.getClass().getName()));

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
        Comic sourceComic = new Comic();
        sourceComic.setId(comicId);
        relations.add(new Relation("comments", "sourceId1", sourceComic.getClass().getName(), "targetId1", Text.class.getName()));

        // targetRelation 1
        Comic targetComic = new Comic();
        targetComic.setId(comicId);
        relations.add(new Relation("comments", "sourceId2", Text.class.getName(), "targetId2", targetComic.getClass().getName()));
        // targetRelation 2
        Comic targetComic2 = new Comic();
        targetComic2.setId(comicId);
        relations.add(new Relation("comments", "sourceId3", Text.class.getName(), "targetId3", targetComic2.getClass().getName()));

        when(_relationService.findAllRelations(comicId)).thenReturn(relations);

        Map<String, List<Relation>> map = _relationService.findAllRelationsGroupedByType(comicId);
        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals(3, map.get("comments").size());
    }

}