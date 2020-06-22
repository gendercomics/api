package net.gendercomics.api.data.repository;

import java.util.List;

import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.KeywordType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeywordRepository extends MongoRepository<Keyword, String> {

    @Query(value = "{ 'type': ?0 }")
    List<Keyword> findByType(KeywordType type);
}
