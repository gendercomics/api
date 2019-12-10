package net.gendercomics.api.data.service;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<Person> findByName(String name) {

        TextCriteria criteria = TextCriteria.forLanguage("de").matching(name);
        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Person> persons = _mongoDbService.getMongoTemplate().find(query, Person.class);
        if (persons != null) {
            return persons;
        }

        return Collections.emptyList();
    }

}
