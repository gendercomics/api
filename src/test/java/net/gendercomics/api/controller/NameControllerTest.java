package net.gendercomics.api.controller;

import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.Name;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration")
@AutoConfigureWebMvc
public class NameControllerTest {

    @Autowired
    private WebApplicationContext _context;

    private MockMvc _mockMvc;

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
    }

    @Test
    public void whenGetAllNames_thenOK() throws Exception {
        Name name = new Name();
        name.setFirstName("Max");
        name.setLastName("Mustermann");

        when(_nameService.findAll()).thenReturn(List.of(name));

        _mockMvc.perform(get("/names").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].lastName", is("Mustermann")));
    }

    @Test
    public void whenGetAllCreators_thenOK() throws Exception {
        Name name = new Name();
        name.setFirstName("Anke");
        name.setLastName("Feuchtenberger");
        name.setSearchable(true);

        when(_nameService.findSearchableNames()).thenReturn(List.of(name));

        _mockMvc.perform(get("/creators").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].lastName", is("Feuchtenberger")));
    }
}
