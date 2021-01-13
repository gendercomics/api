package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
