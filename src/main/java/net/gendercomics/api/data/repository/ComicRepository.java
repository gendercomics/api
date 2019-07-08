package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Comic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComicRepository extends MongoRepository<Comic, String> {
}
