package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.NameRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {NameService.class})
public class NameServiceTest {

    @Autowired
    private NameService _nameService;

    @MockBean
    private NameRepository _nameRepository;

    @MockBean
    private MongoDbService _mongoDbService;

    @Test
    public void findAll() {
        // TODO implement test
    }

    @Test
    public void findByName() {
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