package net.gendercomics.api.data.service;

import java.util.ArrayList;
import java.util.List;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Language;
import net.gendercomics.api.model.Text;
import net.gendercomics.api.model.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ComicServiceTest.TestContextConfiguration.class, ComicService.class})
public class ComicServiceTest {

    @Autowired
    private ComicService _comicService;

    @Autowired
    private ComicRepository _comicRepository;

    @Test
    public void findAll() {
        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic());
        comicList.get(0).setId("comic_id");
        comicList.get(0).setTitle(new Title());
        comicList.get(0).setId("title_id");

        when(_comicRepository.findAll()).thenReturn(comicList);

        List<Comic> result = _comicService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("comic_id", result.get(0).getId());
    }

    @Test
    public void insertComic() {
        Comic comic = new Comic();

        _comicService.insert(comic);

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

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public ComicRepository comicRepository() {
            return mock(ComicRepository.class);
        }
    }
}