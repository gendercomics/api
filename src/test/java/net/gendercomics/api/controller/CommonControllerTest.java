package net.gendercomics.api.controller;

import net.gendercomics.api.data.service.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommonController.class)
@ContextConfiguration(classes = {CommonControllerTest.TestContextConfiguration.class})
public class CommonControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private BuildProperties _buildProperties;

    @MockBean
    private ComicService _comicService;

    @MockBean
    private PersonService _personService;

    @MockBean
    private PublisherService _publisherService;

    @MockBean
    private RoleService _roleService;

    @MockBean
    private KeywordService _keywordService;

    @Disabled
    @Test
    public void getInfo() throws Exception {
        when(_buildProperties.getVersion()).thenReturn("1");
        when(_buildProperties.getName()).thenReturn("name");
        when(_buildProperties.getArtifact()).thenReturn("artifact");
        when(_buildProperties.getGroup()).thenReturn("group");
        when(_buildProperties.getTime()).thenReturn(Instant.now());

        _mockMvc.perform(get("/info"))
                .andExpect(status().isOk());
    }

    @Test
    public void dummy() {
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public BuildProperties buildProperties() {
            return mock(BuildProperties.class);
        }
    }
}