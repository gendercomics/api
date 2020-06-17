package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.KeywordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {KeywordService.class})
public class KeywordServiceTest {

    @MockBean
    private KeywordRepository _keywordRepository;

    @Test
    public void findAll() {
        // TODO implement test
    }

    @Test
    public void findByType() {
        // TODO implement test
    }

    @Test
    public void getKeyword() {
        // TODO implement test
    }

    @Test
    public void insert() {
        // TODO implement test
    }

    @Test
    public void save() {
        // TODO implement test
    }

    @Test
    public void getKeywordCount() {
        // TODO implement test
    }
}