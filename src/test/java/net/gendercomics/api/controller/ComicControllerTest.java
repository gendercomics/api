package net.gendercomics.api.controller;

import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.Comic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
public class ComicControllerTest {

    @Autowired
    private WebApplicationContext _context;

    private MockMvc _mockMvc;

    @MockBean
    private ComicService _comicService;

    @MockBean
    private CommonController _commonController;

    @MockBean
    private PersonService _personService;

    @MockBean
    private KeywordService _keywordService;

    @MockBean
    private PublisherService _publisherService;

    @MockBean
    private RoleService _roleService;

    @MockBean
    private MongoTemplate _mongoTemplate;

    @MockBean
    private RoleRepository _roleRepository;

    @MockBean
    private PersonRepository _personRepository;

    @MockBean
    private KeywordRepository _keywordRepository;

    @MockBean
    private ComicRepository _comicRepository;

    @MockBean
    private PublisherRepository _publisherRepository;

    @MockBean
    private GridFsTemplate _gridFsTemplate;

    @Before
    public void setup() {
        _mockMvc = MockMvcBuilders
                .webAppContextSetup(_context)
                .apply(springSecurity())
                .build();
    }

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

        _mockMvc.perform(get("/comics")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
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
        // TODO implement test
    }

    @Test
    public void insertComic() {
        // TODO implement test
    }

    @Test
    public void getComicCount() throws Exception {
        when(_comicService.getComicCount()).thenReturn(7l);

        _mockMvc.perform(get("/comics/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("<7>s")));
    }

    @Test
    public void getAllComics() {
        // TODO implement test
    }

    @Test
    public void getAllParents() {
        // TODO implement test
    }

    @Test
    public void getComicByTitle() {
        // TODO implement test
    }

    @Test
    public void saveComic() {
        // TODO implement test
    }

}