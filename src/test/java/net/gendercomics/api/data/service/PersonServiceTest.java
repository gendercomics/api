package net.gendercomics.api.data.service;

import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersonServiceTest.TestContextConfiguration.class, PersonService.class})
public class PersonServiceTest {

    @Autowired
    private PersonService _personService;

    @Autowired
    private PersonRepository _personRepository;

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

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public PersonRepository personRepository() {
            return mock(PersonRepository.class);
        }

        @Bean
        public MongoDbService mongoDbService() {
            return mock(MongoDbService.class);
        }

    }
}