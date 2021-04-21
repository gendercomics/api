package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersonService.class})
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

        when(_personRepository.findById("id")).thenReturn(java.util.Optional.of(person));

        Person fetchedPerson = _personService.getPerson("id");
        assertEquals("id", fetchedPerson.getId());
    }

}