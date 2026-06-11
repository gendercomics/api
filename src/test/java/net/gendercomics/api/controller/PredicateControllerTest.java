package net.gendercomics.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.Language;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Predicate;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration")
@AutoConfigureWebMvc
public class PredicateControllerTest {

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
    public void whenGetAllPredicates_thenOK() throws Exception {
        Predicate predicate = new Predicate();
        predicate.setId("id1");
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "ist Teil von");
        values.put(Language.en, "is part of");
        predicate.setValues(values);

        when(_predicateService.findAll()).thenReturn(List.of(predicate));

        _mockMvc.perform(get("/predicates").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is("id1")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenInsertPredicate_thenOK() throws Exception {
        Predicate saved = new Predicate();
        saved.setId("new_id");
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "ist Teil von");
        values.put(Language.en, "is part of");
        saved.setValues(values);
        MetaData meta = new MetaData();
        meta.setCreatedOn(new Date());
        meta.setCreatedBy("mock_user");
        saved.setMetaData(meta);

        when(_predicateService.save(eq("ist Teil von"), eq("is part of"), any())).thenReturn(saved);

        _mockMvc.perform(post("/predicates")
                        .param("de", "ist Teil von")
                        .param("en", "is part of"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("new_id")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenSavePredicate_thenOK() throws Throwable {
        Predicate saved = new Predicate();
        saved.setId("id1");
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "enthält");
        values.put(Language.en, "contains");
        saved.setValues(values);

        doReturn(saved).when(_predicateService).save(eq("id1"), eq("enthält"), eq("contains"), any());

        _mockMvc.perform(put("/predicates/id1")
                        .param("de", "enthält")
                        .param("en", "contains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id1")));
    }
}
