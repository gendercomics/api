package net.gendercomics.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.data.service.PublisherService;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Role;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
public class RoleControllerTest {

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
    private RelationRepository _relationRepository;

    @MockBean
    private PredicateRepository _predicateRepository;

    @MockBean
    private MongoTemplate _mongoTemplate;

    @MockBean
    private GridFsTemplate _gridFsTemplate;

    private ObjectMapper _objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        _mockMvc = MockMvcBuilders
                .webAppContextSetup(_context)
                .apply(springSecurity())
                .build();
        _objectMapper = new ObjectMapper();
    }

    @Test
    public void whenGetAllRoles_thenOK() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.get(0).setId("role_id");
        roles.get(0).setName("role_name");

        when(_roleService.findAll()).thenReturn(roles);

        _mockMvc.perform(get("/roles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetRole_thenOK() throws Exception {
        Role role = new Role();
        role.setId("role_id");
        role.setName("role_name");

        when(_roleService.getRole("role_id")).thenReturn(role);

        _mockMvc.perform(get("/roles/role_id").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("role_id")))
                .andExpect(jsonPath("$.name", is("role_name")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void whenInsertRole_thenOk() throws Exception {
        Role role = new Role();
        role.setName("new_role");

        Role inserted = new Role();
        inserted.setId("new_id");
        inserted.setName("new_role");
        MetaData meta = new MetaData();
        meta.setCreatedOn(new Date());
        meta.setCreatedBy("mock_user");
        inserted.setMetaData(meta);

        when(_roleService.insert(any(), any())).thenReturn(inserted);

        _mockMvc.perform(post("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("new_id")))
                .andExpect(jsonPath("$.name", is("new_role")));
    }

    @Test
    @WithMockUser(username = "mock_user", roles = {"crud_comics"})
    public void whenSaveRole_thenOk() throws Exception {
        Role role = new Role();
        role.setId("role_id");
        role.setName("updated_role");

        Role saved = new Role();
        saved.setId("role_id");
        saved.setName("updated_role");
        MetaData meta = new MetaData();
        meta.setChangedOn(new Date());
        meta.setChangedBy("mock_user");
        saved.setMetaData(meta);

        when(_roleService.save(any(), any())).thenReturn(saved);

        _mockMvc.perform(put("/roles/role_id")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("role_id")))
                .andExpect(jsonPath("$.metaData.changedBy", is("mock_user")));
    }
}