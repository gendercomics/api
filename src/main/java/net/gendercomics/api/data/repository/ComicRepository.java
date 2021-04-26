package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComicRepository extends MongoRepository<Comic, String> {

    @Query(value = "{ 'title': ?0 }")
    Optional<Comic> findByTitle(String title);

    @Query(value = "{ 'type': ?0 }")
    List<Comic> findByType(ComicType type);

    @Query(value = "{ 'comments': { $ne:null } }")
    List<Comic> findWithComments();

}
