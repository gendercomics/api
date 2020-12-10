package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.NameRepository;
import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersonService.class})
public class PersonServiceTest {

    @Autowired
    private PersonService _personService;

    @MockBean
    private PersonRepository _personRepository;

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
    public void getPerson() {
        Person person = new Person();
        person.setId("id");

        when(_personRepository.findById("id")).thenReturn(java.util.Optional.of(person));

        Person fetchedPerson = _personService.getPerson("id");
        assertEquals("id", fetchedPerson.getId());
    }

}