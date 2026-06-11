package net.gendercomics.api.data.service;

import net.gendercomics.api.data.NotFoundException;
import net.gendercomics.api.data.repository.PredicateRepository;
import net.gendercomics.api.data.service.impl.PredicateServiceImpl;
import net.gendercomics.api.model.Language;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PredicateServiceImpl.class})
public class PredicateServiceTest {

    @Autowired
    private PredicateService _predicateService;

    @MockBean
    private PredicateRepository _predicateRepository;

    @Test
    public void whenFindAll_thenReturnSortedList() {
        Predicate p1 = new Predicate();
        p1.setId("id1");
        Map<Language, String> v1 = new HashMap<>();
        v1.put(Language.de, "Zuletzt");
        v1.put(Language.en, "Last");
        p1.setValues(v1);

        Predicate p2 = new Predicate();
        p2.setId("id2");
        Map<Language, String> v2 = new HashMap<>();
        v2.put(Language.de, "Als erstes");
        v2.put(Language.en, "First");
        p2.setValues(v2);

        when(_predicateRepository.findAll()).thenReturn(new ArrayList<>(List.of(p1, p2)));

        List<Predicate> result = _predicateService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Als erstes", result.get(0).getValues().get(Language.de));
        assertEquals("Zuletzt", result.get(1).getValues().get(Language.de));
    }

    @Test
    public void whenSaveWithDeEn_thenInsertNewPredicate() {
        Predicate inserted = new Predicate();
        inserted.setId("new_id");
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "ist Teil von");
        values.put(Language.en, "is part of");
        inserted.setValues(values);

        when(_predicateRepository.insert(any(Predicate.class))).thenReturn(inserted);

        Predicate result = _predicateService.save("ist Teil von", "is part of", "testUser");

        assertNotNull(result);
        assertEquals("new_id", result.getId());
        assertEquals("ist Teil von", result.getValues().get(Language.de));
        assertEquals("is part of", result.getValues().get(Language.en));
    }

    @Test
    public void whenSaveNewPredicate_thenMetaDataSet() {
        Predicate predicate = new Predicate();
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "enthält");
        values.put(Language.en, "contains");
        predicate.setValues(values);

        when(_predicateRepository.insert(any(Predicate.class))).thenReturn(predicate);

        Predicate result = _predicateService.save(predicate, "testUser");

        assertNotNull(result.getMetaData());
        assertEquals("testUser", result.getMetaData().getCreatedBy());
        assertNotNull(result.getMetaData().getCreatedOn());
    }

    @Test
    public void whenSaveExistingPredicate_thenMetaDataUpdated() {
        Predicate predicate = new Predicate();
        predicate.setId("id1");
        MetaData meta = new MetaData();
        meta.setCreatedOn(new Date());
        meta.setCreatedBy("creator");
        predicate.setMetaData(meta);
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "enthält");
        values.put(Language.en, "contains");
        predicate.setValues(values);

        when(_predicateRepository.save(any(Predicate.class))).thenReturn(predicate);

        Predicate result = _predicateService.save(predicate, "updater");

        assertNotNull(result.getMetaData());
        assertEquals("updater", result.getMetaData().getChangedBy());
        assertNotNull(result.getMetaData().getChangedOn());
    }

    @Test
    public void whenSaveById_thenUpdateValues() throws Throwable {
        Predicate existing = new Predicate();
        existing.setId("id1");
        MetaData meta = new MetaData();
        meta.setCreatedOn(new Date());
        existing.setMetaData(meta);
        Map<Language, String> values = new HashMap<>();
        values.put(Language.de, "alt");
        values.put(Language.en, "old");
        existing.setValues(values);

        when(_predicateRepository.findById("id1")).thenReturn(Optional.of(existing));
        when(_predicateRepository.save(any(Predicate.class))).thenReturn(existing);

        Predicate result = _predicateService.save("id1", "neu", "new", "testUser");

        assertEquals("neu", result.getValues().get(Language.de));
        assertEquals("new", result.getValues().get(Language.en));
    }

    @Test
    public void whenSaveByIdNotFound_thenThrowNotFoundException() {
        when(_predicateRepository.findById("unknown")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> {
                    try {
                        _predicateService.save("unknown", "de", "en", "testUser");
                    } catch (NotFoundException e) {
                        throw e;
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                });
    }

    @Test
    public void whenDelete_thenDeleteById() {
        _predicateService.delete("id1");
        verify(_predicateRepository).deleteById("id1");
    }
}
