package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.NameRepository;
import net.gendercomics.api.data.service.impl.NameServiceImpl;
import net.gendercomics.api.model.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NameServiceImpl.class})
public class NameServiceTest {

    @Autowired
    private NameService _nameService;

    @MockBean
    private NameRepository _nameRepository;

    @MockBean
    private MongoDbService _mongoDbService;

    @Test
    public void whenFindSearchableNames_thenReturnSortedList() {
        List<Name> mockList = new ArrayList<>();
        mockList.add(new Name());
        mockList.get(0).setFirstName("Ulli");
        mockList.get(0).setLastName("Lust");
        mockList.get(0).setSearchable(true);
        mockList.add(new Name());
        mockList.get(1).setFirstName("Anke");
        mockList.get(1).setLastName("Feuchtenberger");
        mockList.get(1).setSearchable(true);

        when(_nameRepository.findIsSearchable()).thenReturn(mockList);

        List<Name> result = _nameService.findSearchableNames();
        assertNotNull(result);
        assertEquals("Feuchtenberger", result.get(0).getLastName());
        assertEquals("Ulli", result.get(1).getFirstName());
    }

    @Test
    public void whenSaveName_withNoId_thenInsert() {
        Name name = new Name();
        name.setFirstName("Max");
        name.setLastName("Mustermann");

        when(_nameRepository.insert(any(Name.class))).thenReturn(name);

        Name result = _nameService.saveName(name);
        assertNotNull(result);
        assertEquals("Max", result.getFirstName());
    }

    @Test
    public void whenSaveName_withId_thenSave() {
        Name name = new Name();
        name.setId("id");
        name.setFirstName("Max");
        name.setLastName("Mustermann");

        when(_nameRepository.save(any(Name.class))).thenReturn(name);

        Name result = _nameService.saveName(name);
        assertNotNull(result);
        assertEquals("id", result.getId());
    }

    @Test
    public void whenFindAll_thenReturnSortedNameList() {
        List<Name> mockList = new ArrayList<>();
        mockList.add(new Name());
        mockList.get(0).setFirstName("Ulli");
        mockList.get(0).setLastName("Lust");
        mockList.add(new Name());
        mockList.get(1).setFirstName("Anke");
        mockList.get(1).setLastName("Feuchtenberger");

        when(_nameRepository.findAll()).thenReturn(mockList);

        List<Name> nameList = _nameService.findAll();
        assertNotNull(nameList);
        assertEquals("Feuchtenberger", nameList.get(0).getLastName());
        assertEquals("Ulli", nameList.get(1).getFirstName());
    }

}