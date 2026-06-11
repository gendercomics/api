package net.gendercomics.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.*;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Text;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration")
@AutoConfigureWebMvc
public class TextControllerTest {

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
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenInsertText_thenOK() throws Exception {
        Text saved = new Text();
        saved.setId("new_id");
        saved.setValue("some text value");
        MetaData meta = new MetaData();
        meta.setCreatedOn(new Date());
        meta.setCreatedBy("mock_user");
        saved.setMetaData(meta);

        when(_textService.save(any(), any())).thenReturn(saved);

        _mockMvc.perform(post("/texts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"some text value\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("new_id")))
                .andExpect(jsonPath("$.value", is("some text value")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void givenAuthorizedUser_whenUpdateText_thenOK() throws Exception {
        Text text = new Text();
        text.setId("id1");
        text.setValue("updated text");

        Text saved = new Text();
        saved.setId("id1");
        saved.setValue("updated text");
        MetaData meta = new MetaData();
        meta.setChangedOn(new Date());
        meta.setChangedBy("mock_user");
        saved.setMetaData(meta);

        when(_textService.save(any(), any())).thenReturn(saved);

        _mockMvc.perform(put("/texts/id1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(text)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("id1")))
                .andExpect(jsonPath("$.metaData.changedBy", is("mock_user")));
    }

    @Test
    public void givenNoAuth_whenInsertText_thenUnauthorized() throws Exception {
        _mockMvc.perform(post("/texts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"some text\""))
                .andExpect(status().isUnauthorized());
    }
}
