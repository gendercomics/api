package net.gendercomics.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gendercomics.api.data.NotFoundException;
import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import net.gendercomics.api.model.MetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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
    private NameRepository _nameRepository;

    @MockBean
    private TextRepository _textRepository;

    @MockBean
    private RelationService _relationService;

    @MockBean
    private RelationRepository _relationRepository;

    /**
     * MongoDB mocks
     **/
    @MockBean
    private MongoTemplate _mongoTemplate;

    @MockBean
    private GridFsTemplate _gridFsTemplate;

    private ObjectMapper _objectMapper;

    @BeforeEach
    public void setup() {
        _mockMvc = MockMvcBuilders
                .webAppContextSetup(_context)
                .apply(springSecurity())
                .build();
        _objectMapper = new ObjectMapper();
    }

    @Test
    public void whenGetAllComics_thenOk() throws Exception {
        List<Comic> comicList = new ArrayList<>();

        comicList.add(new Comic());
        comicList.get(0).setId("id1");
        comicList.get(0).setTitle("Wonderwoman");

        comicList.add(new Comic());
        comicList.get(1).setId("id2");
        comicList.get(1).setTitle("Gift");

        when(_comicService.findAll()).thenReturn(comicList);

        _mockMvc.perform(get("/comics")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].title", is("Wonderwoman")))
                .andExpect(jsonPath("$.[1].title", is("Gift")));
    }

    @Test
    public void whenGetComic_thenOk() throws Exception {
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
    public void whenGetComicAsXml_thenOk() throws Exception {
        Comic comic = new Comic();
        comic.setId("4711");
        comic.setTitle("testComic");

        when(_comicService.getComic("4711")).thenReturn(comic);

        _mockMvc.perform(get("/comics/4711/xml"))
                .andExpect(status().isOk());
        //.andExpect(xpath("/Comic/id", "%s").string(containsString("4711")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenSaveNewComic_thenInsertComicOk() throws Exception {
        Comic comic = new Comic();
        comic.setTitle("testComic");

        Comic insertedComic = new Comic();
        insertedComic.setId("4711");
        insertedComic.setTitle("testComic");

        when(_comicService.save(any(), any())).thenReturn(insertedComic);

        _mockMvc.perform(post("/comics/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(_objectMapper.writeValueAsString(comic)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("4711")))
                .andExpect(jsonPath("$.title", is("testComic")));
    }

    @Disabled("check security config")
    @Test
    @WithAnonymousUser
    public void givenAnonymousUser_whenInsertComic_thenForbidden() throws Exception {
        Comic comic = new Comic();
        comic.setTitle("testComic");

        _mockMvc.perform(post("/comics/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(_objectMapper.writeValueAsString(comic)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenSaveComic_thenOk() throws Exception {
        Comic comic = new Comic();
        comic.setId("4711");
        comic.setTitle("testComic");

        Comic savedComic = new Comic();
        savedComic.setId("4711");
        savedComic.setTitle("testComic");
        savedComic.setMetaData(new MetaData());
        savedComic.getMetaData().setChangedOn(new Date());

        when(_comicService.save(any(), any())).thenReturn(savedComic);

        _mockMvc.perform(put("/comics/4711")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(_objectMapper.writeValueAsString(comic)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("4711")))
                .andExpect(jsonPath("$.title", is("testComic")))
                .andExpect(jsonPath("$.metaData.changedOn").isNotEmpty());
    }

    @Test
    public void whenGetComicCount_thenOk() throws Exception {
        when(_comicService.getComicCount()).thenReturn(7L);

        _mockMvc.perform(get("/comics/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(7)));
    }

    @Test
    public void whenGetAllParents_thenOk() throws Exception {
        List<Comic> comicList = new ArrayList<>();

        comicList.add(new Comic());
        comicList.get(0).setId("id");
        comicList.get(0).setTitle("test_anthology");
        comicList.get(0).setType(ComicType.anthology);

        when(_comicService.findByTypes(ComicType.anthology, ComicType.magazine, ComicType.series)).thenReturn(comicList);

        _mockMvc.perform(get("/comics/parents")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].type", is(ComicType.anthology.name())));
    }

    @Test
    public void whenGetComicByTitle_thenOk() throws NotFoundException, Exception {
        String title = "my_title";

        Comic comic = new Comic();
        comic.setId("id");
        comic.setTitle(title);

        when(_comicService.findByTitle(title)).thenReturn(comic);

        _mockMvc.perform(get("/comics/title/" + title)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(title)));
    }

}