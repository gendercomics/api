package net.gendercomics.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ComicControllerTest.TestContextConfiguration.class})
@WebMvcTest(ComicController.class)
public class RoleControllerTest {

    @Test
    public void getAllRoles() {
    }

    @Test
    public void insertRole() {
    }

    @Test
    public void saveComic() {
    }
}