package net.gendercomics.api.controller;

import java.util.ArrayList;
import java.util.List;

import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.ApiInfo;
import net.gendercomics.api.model.Comic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ComicController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ComicControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private ComicService _comicService;

    @Test
    public void findAll() throws Exception {
        List<Comic> comicList = new ArrayList<>();
        comicList.add(new Comic());
        comicList.get(0).setId("id1");
        comicList.get(0).setTitle("Wonderwoman");
        comicList.add(new Comic());
        comicList.get(1).setId("id2");
        comicList.get(1).setTitle("Gift");

        when(_comicService.findAll()).thenReturn(comicList);

        _mockMvc.perform(get("/comics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title", is("Wonderwoman")))
                .andExpect(jsonPath("$.[1].title", is("Gift")));
    }

    @Test
    public void getComic() throws Exception {
        Comic comic = new Comic();
        comic.setId("4711");
        comic.setTitle("testComic");

        when(_comicService.getComic("4711")).thenReturn(comic);

        _mockMvc.perform(get("/comics/4711"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("4711")))
                .andExpect(jsonPath("$.title", is("testComic")));
    }

    @Test
    public void getComicAsXml() {
        // TODO
    }

    @Test
    public void insertComic() {
        // TODO
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public ComicService comicService() {
            return mock(ComicService.class);
        }

        @Bean
        public ApiInfo apiInfo() {
            return mock(ApiInfo.class);
        }
    }
}