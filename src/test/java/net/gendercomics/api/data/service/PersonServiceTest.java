package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.data.service.impl.PersonServiceImpl;
import net.gendercomics.api.model.Name;
import net.gendercomics.api.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersonServiceImpl.class})
public class PersonServiceTest {

    @Autowired
    private PersonService _personService;

    @MockBean
    private PersonRepository _personRepository;

    @MockBean
    private NameService _nameService;

    @MockBean
    private MongoDbService _mongoDbService;

    @Test
    public void getPerson() {
        Person person = new Person();
        person.setId("id");

        when(_personRepository.findById("id")).thenReturn(Optional.of(person));

        Person fetchedPerson = _personService.getPerson("id");
        assertEquals("id", fetchedPerson.getId());
    }

    @Test
    public void whenFindAll_thenReturnSortedList() {
        Name name1 = new Name();
        name1.setLastName("Zweig");
        name1.setFirstName("Stefan");
        Person p1 = new Person();
        p1.setId("p1");
        p1.setNames(List.of(name1));

        Name name2 = new Name();
        name2.setLastName("Anke");
        name2.setFirstName("A");
        Person p2 = new Person();
        p2.setId("p2");
        p2.setNames(List.of(name2));

        when(_personRepository.findAll()).thenReturn(new ArrayList<>(List.of(p1, p2)));

        List<Person> result = _personService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("p2", result.get(0).getId());
    }

    @Test
    public void whenInsert_thenMetaDataSet() {
        Name name = new Name();
        name.setFirstName("Max");
        name.setLastName("Mustermann");

        Person person = new Person();
        person.setNames(new ArrayList<>(List.of(name)));

        when(_nameService.saveName(any(Name.class))).thenReturn(name);
        when(_personRepository.insert(any(Person.class))).thenReturn(person);

        Person inserted = _personService.insert(person, "testUser");
        assertNotNull(inserted.getMetaData());
        assertEquals("testUser", inserted.getMetaData().getCreatedBy());
        assertNotNull(inserted.getMetaData().getCreatedOn());
    }

    @Test
    public void whenSave_thenMetaDataUpdated() {
        Name name = new Name();
        name.setFirstName("Max");

        Person person = new Person();
        person.setId("id");
        person.setNames(new ArrayList<>(List.of(name)));

        when(_nameService.saveName(any(Name.class))).thenReturn(name);
        when(_personRepository.save(any(Person.class))).thenReturn(person);

        Person saved = _personService.save(person, "testUser");
        assertNotNull(saved.getMetaData());
        assertEquals("testUser", saved.getMetaData().getChangedBy());
        assertNotNull(saved.getMetaData().getChangedOn());
    }

    @Test
    public void whenGetPersonCount_thenReturnCount() {
        when(_personRepository.count()).thenReturn(42L);
        assertEquals(42L, _personService.getPersonCount());
    }

    @Test
    public void whenDelete_thenPersonAndNamesDeleted() {
        Name name = new Name();
        name.setId("name_id");

        Person person = new Person();
        person.setId("id");
        person.setNames(new ArrayList<>(List.of(name)));

        when(_personRepository.findById("id")).thenReturn(Optional.of(person));

        _personService.delete("id");

        verify(_nameService).deleteName(name);
        verify(_personRepository).delete(person);
    }
}