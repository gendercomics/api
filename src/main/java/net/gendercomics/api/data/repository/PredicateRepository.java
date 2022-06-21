package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Predicate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredicateRepository extends MongoRepository<Predicate, String> {
}
