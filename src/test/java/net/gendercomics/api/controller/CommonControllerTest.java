package net.gendercomics.api.controller;

import net.gendercomics.api.model.ApiInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CommonController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CommonControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private ApiInfo _apiInfo;

    @Test
    public void getInfo() throws Exception {
        _mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationName", is("gendercomics-api")))
                .andExpect(jsonPath("$.version", is("1")));
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public ApiInfo apiInfo() {
            return new ApiInfo();
        }
    }
}