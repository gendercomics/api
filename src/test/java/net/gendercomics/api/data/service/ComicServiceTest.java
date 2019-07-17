package net.gendercomics.api.data.service;

import java.util.ArrayList;
import java.util.List;

import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
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
        comicList.get(0).setId("id");
        comicList.get(0).setTitle("title");

        when(_comicRepository.findAll()).thenReturn(comicList);

        List<Comic> result = _comicService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("id", result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
    }

    @Test
    public void insertComic() {
        Comic comic = new Comic();
        comic.setTitle("title");

        _comicService.insert(comic);

        verify(_comicRepository).insert(comic);
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public ComicRepository comicRepository() {
            return mock(ComicRepository.class);
        }
    }
}