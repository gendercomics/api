package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Query(value = "{ 'names.id': ?0 }")
    Optional<Person> findByNameId(String id);
}
