package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ComicService.class)
public class ComicServiceTest {

    @Autowired
    private ComicService _comicService;

    @MockBean
    private ComicRepository _comicRepository;

    @MockBean
    private RelationService _relationService;

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

    @Test
    public void findByTitle() {
        // TODO
    }

    @Test
    public void titleExists() {
        // TODO
    }

    @Test
    public void getComicAsXml() {
        // TODO
    }

    @Test
    public void getComic() {
        // TODO
    }

}