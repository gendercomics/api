package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.data.service.MongoDbService;
import net.gendercomics.api.data.service.NameService;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Name;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final MongoDbService _mongoDbService;
    private final PersonRepository _personRepository;
    private final NameService _nameService;

    @Override
    public List<Person> findAll() {
        List<Person> persons = _personRepository.findAll();
        Collections.sort(persons);
        return persons;
    }

    @Override
    public Person insert(Person person, String userName) {
        log.debug("userName={} tries to insert person={}", userName, person.toString());

        person.setMetaData(new MetaData());
        person.getMetaData().setCreatedOn(new Date());
        person.getMetaData().setCreatedBy(userName);

        person.setNames(saveNames(person.getNames()));

        return _personRepository.insert(person);
    }

    @Override
    public Person save(Person person, String userName) {
        log.debug("userName={} tries to save person={}", userName, person.toString());
        if (person.getMetaData() == null) {
            person.setMetaData(new MetaData());
        }

        person.setNames(saveNames(person.getNames()));
        // TODO delete names

        person.getMetaData().setChangedOn(new Date());
        person.getMetaData().setChangedBy(userName);

        return _personRepository.save(person);
    }

    private List<Name> saveNames(List<Name> names) {
        names.forEach(_nameService::saveName);
        return names;
    }

    @Override
    public Person getPerson(String id) {
        return _personRepository.findById(id).orElse(null);
    }

    @Override
    public long getPersonCount() {
        return _personRepository.count();
    }

    @Override
    public void delete(String id) {
        Person person = getPerson(id);
        person.getNames().forEach(_nameService::deleteName);
        _personRepository.delete(person);
    }
}
