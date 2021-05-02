package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Name;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NameRepository extends MongoRepository<Name, String> {

    @Query(value = "{ 'searchable': true }")
    List<Name> findIsSearchable();
}
