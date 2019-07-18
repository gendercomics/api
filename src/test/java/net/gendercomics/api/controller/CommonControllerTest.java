package net.gendercomics.api.controller;

import java.time.Instant;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommonController.class)
@ContextConfiguration(classes = {CommonControllerTest.TestContextConfiguration.class})
public class CommonControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private BuildProperties _buildProperties;

    @Ignore
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

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public BuildProperties buildProperties() {
            return mock(BuildProperties.class);
        }
    }
}