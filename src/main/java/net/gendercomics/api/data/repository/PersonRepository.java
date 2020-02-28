package net.gendercomics.api.data.repository;

import java.util.List;

import net.gendercomics.api.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PersonRepository extends MongoRepository<Person, String> {
}
