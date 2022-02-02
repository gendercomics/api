package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import net.gendercomics.api.model.Comic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ComicServiceImpl.class)
public class ComicServiceTest {

    @Autowired
    private ComicServiceImpl _comicService;

    @MockBean
    private ComicRepository _comicRepository;

    @MockBean
    private RelationService _relationService;

    @MockBean
    private TextService _textService;

    @Test
    public void findAll() {
        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic());
        comicList.get(0).setId("comic_id");
        comicList.get(0).setTitle("comic_title");

        when(_comicRepository.findAll()).thenReturn(comicList);

        List<Comic> result = _comicService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("comic_id", result.get(0).getId());
    }

    @Test
    public void insertComic() {
        Comic comic = new Comic();
        _comicService.save(comic, "test-user");
        verify(_comicRepository).insert(comic);
    }

}