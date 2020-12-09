package net.gendercomics.api.data.service;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.NameRepository;
import net.gendercomics.api.data.repository.PersonRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Name;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PersonService {

    private final MongoDbService _mongoDbService;
    private final PersonRepository _personRepository;
    private final NameRepository _nameRepository;

    public List<Person> findAll() {
        List<Person> persons = _personRepository.findAll();
        log.debug("#persons={}", persons.size());
        //Collections.sort(persons);
        return persons;
    }

    /*
    public List<Person> findByName(String name) {

        TextCriteria criteria = TextCriteria.forLanguage("de").matching(name);
        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Person> persons = _mongoDbService.getMongoTemplate().find(query, Person.class);
        if (persons != null) {
            return persons;
        }

        return Collections.emptyList();
    }
*/
    public Person insert(Person person, String userName) {
        log.debug("userName={} tries to insert person={}", userName, person.toString());

        person.setMetaData(new MetaData());
        person.getMetaData().setCreatedOn(new Date());
        person.getMetaData().setCreatedBy(userName);

        person.setNames(saveNames(person.getNames()));

        return _personRepository.insert(person);
    }

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
        names.forEach(name -> saveName(name));
        return names;
    }

    private Name saveName(Name name) {
        return name.getId() == null ? _nameRepository.insert(name) : _nameRepository.save(name);
    }

    public Person getPerson(String id) {
        return _personRepository.findById(id).orElse(null);
    }

    public long getPersonCount() {
        return _personRepository.count();
    }
}
