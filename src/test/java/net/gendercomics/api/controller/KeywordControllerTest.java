package net.gendercomics.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
public class KeywordControllerTest {

    @Autowired
    private WebApplicationContext _context;

    private MockMvc _mockMvc;

    @Test
    public void whenGetKeywords_thenOK() {
        // TODO implement test
    }

    @Test
    public void whenGetKeyword_thenOK() {
        // TODO implement test
    }

    @Test
    public void whenInsertKeyword_thenOK() {
        // TODO implement test
    }

    @Test
    public void whenSaveKeyword_thenOK() {
        // TODO implement test
    }
}