package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.data.repository.TextRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Predicate;
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
        // TODO re-implement test
    }

}