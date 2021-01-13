package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.NameRepository;
import net.gendercomics.api.data.repository.PersonRepository;
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
public class NameService {

    private final MongoDbService _mongoDbService;
    private final NameRepository _nameRepository;

    public List<Name> findAll() {
        List<Name> names = _nameRepository.findAll();
        log.debug("#names={}", names.size());
        Collections.sort(names);
        return names;
    }

    public List<Name> findSearchableNames() {
        List<Name> names = _nameRepository.findIsSearchable();
        log.debug("#names={}", names.size());
        Collections.sort(names);
        return names;
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

    public Name saveName(Name name) {
        name.setEmptyStringsNull();
        return name.getId() == null ? _nameRepository.insert(name) : _nameRepository.save(name);
    }

    public void deleteName(Name name) {
        _nameRepository.delete(name);
    }

}
