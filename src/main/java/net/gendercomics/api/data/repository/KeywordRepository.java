package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Keyword;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeywordRepository extends MongoRepository<Keyword, String> {
}
