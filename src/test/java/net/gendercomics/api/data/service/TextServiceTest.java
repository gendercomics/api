package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.TextRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Text;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TextService.class)
public class TextServiceTest {

    @Autowired
    private TextService _textService;

    @MockBean
    private TextRepository _textRepository;

    @Test
    public void whenSaveNew_ThenReturnedSavedText() {
        Text txt = new Text();
        txt.setValue("txt");

        when(_textRepository.insert(any(Text.class))).thenReturn(txt);

        Text savedText = _textService.save(txt, "userName");
        assertNotNull(savedText);
        assertNotNull(savedText.getMetaData());
        assertNotNull(savedText.getMetaData().getCreatedOn());
    }

    @Test
    public void whenSaveExisting_ThenReturnedSavedText() {
        Text txt = new Text();
        txt.setValue("txt");
        txt.setId("id");
        txt.setMetaData(new MetaData());

        when(_textRepository.save(txt)).thenReturn(txt);

        Text savedText = _textService.save(txt, "userName");
        assertNotNull(savedText);
        assertNotNull(savedText.getMetaData());
        assertEquals("userName", savedText.getMetaData().getChangedBy());
    }
}