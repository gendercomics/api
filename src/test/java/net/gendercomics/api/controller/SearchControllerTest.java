package net.gendercomics.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchFilter;
import net.gendercomics.api.model.SearchInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration")
@AutoConfigureWebMvc
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext _context;

    private MockMvc _mockMvc;
    private ObjectMapper _objectMapper;

    @MockBean private ComicService _comicService;
    @MockBean private CommonController _commonController;
    @MockBean private PersonService _personService;
    @MockBean private KeywordService _keywordService;
    @MockBean private PublisherService _publisherService;
    @MockBean private RoleService _roleService;
    @MockBean private NameService _nameService;
    @MockBean private TextService _textService;
    @MockBean private SearchService _searchService;
    @MockBean private PredicateService _predicateService;
    @MockBean private MigrationService _migrationService;

    @MockBean private RoleRepository _roleRepository;
    @MockBean private PersonRepository _personRepository;
    @MockBean private KeywordRepository _keywordRepository;
    @MockBean private ComicRepository _comicRepository;
    @MockBean private PublisherRepository _publisherRepository;
    @MockBean private NameRepository _nameRepository;
    @MockBean private TextRepository _textRepository;
    @MockBean private RelationRepository _relationRepository;
    @MockBean private PredicateRepository _predicateRepository;

    @MockBean private MongoTemplate _mongoTemplate;
    @MockBean private GridFsTemplate _gridFsTemplate;

    @BeforeEach
    public void setUp() {
        _mockMvc = MockMvcBuilders.webAppContextSetup(_context).apply(springSecurity()).build();
        _objectMapper = new ObjectMapper();
    }

    @Test
    public void whenSearchByTerm_thenReturnComics() throws Exception {
        Comic comic = new Comic();
        comic.setId("id1");
        comic.setTitle("Wonderwoman");

        when(_searchService.searchAndReturnComics(eq("wonder"))).thenReturn(List.of(comic));

        _mockMvc.perform(post("/search").param("searchTerm", "wonder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].title", is("Wonderwoman")));
    }

    @Test
    public void whenSearchWeb_thenReturnComics() throws Exception {
        Comic comic = new Comic();
        comic.setId("id1");
        comic.setTitle("Wonderwoman");

        SearchInput input = new SearchInput("wonder", new SearchFilter(true, true, true, true), "de");

        when(_searchService.searchAndReturnComics(any(SearchInput.class))).thenReturn(List.of(comic));

        _mockMvc.perform(post("/search-web")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].title", is("Wonderwoman")));
    }

    @Test
    public void whenSearchEmpty_thenReturnEmptyList() throws Exception {
        when(_searchService.searchAndReturnComics(eq("unknown_term_xyz"))).thenReturn(List.of());

        _mockMvc.perform(post("/search").param("searchTerm", "unknown_term_xyz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void whenDownloadSearch_thenReturnAttachment() throws Exception {
        Comic comic = new Comic();
        comic.setId("id1");
        comic.setTitle("Wonderwoman");

        SearchInput input = new SearchInput("wonder", new SearchFilter(true, true, true, true), "de");

        when(_searchService.searchAndReturnComics(any(SearchInput.class))).thenReturn(List.of(comic));
        when(_searchService.convertResultToHarvard(any())).thenReturn("Wonderwoman. 2024.");

        _mockMvc.perform(post("/search/download")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment;filename=comics-wonder.txt"));
    }
}
