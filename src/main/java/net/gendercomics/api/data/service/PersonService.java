package net.gendercomics.api.data.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PersonService {

    private final MongoDbService _mongoDbService;
    private final PersonRepository _personRepository;

    public List<Person> findAll() {
        List<Person> persons = _personRepository.findAll();
        log.debug("#persons={}", persons.size());
        return persons;
    }

    public List<Person> findByName(String name) {

        TextCriteria criteria = TextCriteria.forLanguage("de").matching(name);
        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Person> persons = _mongoDbService.getMongoTemplate().find(query, Person.class);
        if (persons != null) {
            return persons;
        }

        return Collections.emptyList();
    }

    public Person insert(Person person, String userName) {
        log.debug("userName={} tries to insert person={}", userName, person.toString());

        person.setMetaData(new MetaData());
        person.getMetaData().setCreatedOn(new Date());
        person.getMetaData().setCreatedBy(userName);

        return _personRepository.insert(person);
    }

    public Person save(Person person, String userName) {
        log.debug("userName={} tries to save person={}", userName, person.toString());
        if (person.getMetaData() == null) {
            person.setMetaData(new MetaData());
        }
        person.getMetaData().setChangedOn(new Date());
        person.getMetaData().setCreatedBy(userName);

        return _personRepository.insert(person);
    }
}
