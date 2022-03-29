package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.KeywordRepository;
import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.KeywordType;
import net.gendercomics.api.model.KeywordValue;
import net.gendercomics.api.model.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {KeywordService.class})
public class KeywordServiceTest {

    @Autowired
    private KeywordService _keywordService;

    @MockBean
    private KeywordRepository _keywordRepository;

    @Test
    public void whenFindAll_ThenReturnKeywordList() {
        List<Keyword> keywordList = new ArrayList<>();
        keywordList.add(new Keyword());

        keywordList.get(0).setId("id");
        keywordList.get(0).setType(KeywordType.content);
        keywordList.get(0).setValues(new HashMap<>());

        KeywordValue keywordValue = new KeywordValue();
        keywordValue.setLanguage(Language.de);
        keywordValue.setName("keywordName_de");
        keywordValue.setDescription("keywordDescription_de");
        keywordList.get(0).getValues().put(keywordValue.getLanguage(), keywordValue);

        when(_keywordRepository.findAll()).thenReturn(keywordList);

        List<Keyword> keywords = _keywordService.findAll();
        assertNotNull(keywords);
        assertTrue(keywords.size() == 1);
        assertEquals(KeywordType.content, keywords.get(0).getType());

        assertEquals("id", keywords.get(0).getId());
        assertEquals(Language.de, keywords.get(0).getValues().get(Language.de).getLanguage());
        assertEquals("keywordName_de", keywords.get(0).getValues().get(Language.de).getName());
        assertEquals("keywordDescription_de", keywords.get(0).getValues().get(Language.de).getDescription());
    }

    @Test
    public void whenFindByType_ThenReturnKeywordList() {
        List<Keyword> keywordList = new ArrayList<>();
        keywordList.add(new Keyword());

        keywordList.get(0).setId("id");
        keywordList.get(0).setType(KeywordType.content);
        keywordList.get(0).setValues(new HashMap<>());

        KeywordValue keywordValue = new KeywordValue();
        keywordValue.setLanguage(Language.de);
        keywordValue.setName("keywordName_de");
        keywordValue.setDescription("keywordDescription_de");
        keywordList.get(0).getValues().put(keywordValue.getLanguage(), keywordValue);

        when(_keywordRepository.findByType(KeywordType.content)).thenReturn(keywordList);

        List<Keyword> keywords = _keywordService.findByType(KeywordType.content.name());
        assertNotNull(keywords);
        assertTrue(keywords.size() == 1);
        assertEquals(KeywordType.content, keywords.get(0).getType());

        assertEquals("id", keywords.get(0).getId());
        assertEquals(Language.de, keywords.get(0).getValues().get(Language.de).getLanguage());
        assertEquals("keywordName_de", keywords.get(0).getValues().get(Language.de).getName());
        assertEquals("keywordDescription_de", keywords.get(0).getValues().get(Language.de).getDescription());
    }

    @Test
    public void whenGetKeyword_TheReturnFoundKeyword() {
        Keyword keyword = new Keyword();
        keyword.setId("id");
        keyword.setType(KeywordType.content);

        when(_keywordRepository.findById("id")).thenReturn(Optional.of(keyword));

        Keyword foundKeyword = _keywordService.getKeyword("id");
        assertNotNull(foundKeyword);
        assertEquals("id", foundKeyword.getId());
        assertEquals(KeywordType.content, foundKeyword.getType());
    }

    @Test
    public void whenInsert_ThenReturnInsertedKeyword() {
        Keyword keyword = new Keyword();
        keyword.setId("id");
        keyword.setType(KeywordType.content);

        when(_keywordRepository.insert(any(Keyword.class))).thenReturn(keyword);

        Keyword insertedKeyword = _keywordService.save(keyword, "username");
        assertNotNull(insertedKeyword);
        assertEquals("id", insertedKeyword.getId());
        assertEquals(KeywordType.content, insertedKeyword.getType());
        assertNotNull(insertedKeyword.getMetaData());
        assertEquals("username", insertedKeyword.getMetaData().getCreatedBy());
        assertNotNull(insertedKeyword.getMetaData().getCreatedOn());
    }

    @Test
    public void whenSave_ThenReturnSavedKeyword() {
        Keyword keyword = new Keyword();
        keyword.setId("id");
        keyword.setType(KeywordType.content);

        when(_keywordRepository.save(any(Keyword.class))).thenReturn(keyword);

        Keyword savedKeyword = _keywordService.save(keyword, "username");
        assertNotNull(savedKeyword);
        assertEquals("id", savedKeyword.getId());
        assertEquals(KeywordType.content, savedKeyword.getType());
        assertNotNull(savedKeyword.getMetaData());
        assertEquals("username", savedKeyword.getMetaData().getChangedBy());
        assertNotNull(savedKeyword.getMetaData().getChangedOn());
    }

    @Test
    public void getKeywordCount() {
        when(_keywordRepository.count()).thenReturn(666L);

        assertEquals(666L, _keywordService.getKeywordCount());
    }
}