package net.gendercomics.api.controller;

import net.gendercomics.api.data.repository.*;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.data.service.PublisherService;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import net.gendercomics.api.model.Role;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
public class RoleControllerTest {

    @Autowired
    private WebApplicationContext _context;

    private MockMvc _mockMvc;

    @MockBean
    private ComicServiceImpl _comicService;

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

    /**
     * MongoDB mocks
     **/
    @MockBean
    private MongoTemplate _mongoTemplate;

    @MockBean
    private GridFsTemplate _gridFsTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        _mockMvc = MockMvcBuilders
                .webAppContextSetup(_context)
                .apply(springSecurity())
                .build();
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

    @Disabled("incomplete")
    @Test
    public void whenGetRole_thenOK() throws Exception {
        String id = "role_id";

        _mockMvc.perform(get("/roles/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInsertRole_thenOk() {
        // TODO implement test
    }


    @Test
    public void whenSaveRole_thenOk() {
        // TODO implement test
    }
}