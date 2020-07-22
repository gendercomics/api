package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Name;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NameRepository extends MongoRepository<Name, String> {

}
