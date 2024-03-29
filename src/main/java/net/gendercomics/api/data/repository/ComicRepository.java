package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ComicRepository extends MongoRepository<Comic, String> {

    @Query(value = "{ 'title': ?0 }")
    List<Comic> findByTitle(String title);

    @Query(value = "{ 'type': ?0 }")
    List<Comic> findByType(ComicType type);

    @Query(value = "{ 'comments': { $ne:null } }")
    List<Comic> findWithComments();

    @Query(value = "{}", fields = "{ 'title' : 1, 'subTitle' : 1, 'type' : 1, 'creators' : 1, 'publishers' : 1, 'issue' : 1, 'issueTitle' : 1, 'seriesList' : 1, 'series' : 1, 'partOf' : 1, 'metaData' : 1 }")
    List<Comic> findAllLimitFields();

    @Query(value = "{ 'creators.name.id': ?0 }")
    List<Comic> getByCreatorNameId(String id);

    @Query(value = "{ 'publishers.id': ?0 }")
    List<Comic> getByPublisherId(String id);

    @Query(value = "{ 'seriesList.comic.id': ?0 }")
    List<Comic> getBySeriesId(String id);

    @Query(value = "{ 'partOf.comic.id': ?0 }")
    List<Comic> getByPartOf(String id);
}
