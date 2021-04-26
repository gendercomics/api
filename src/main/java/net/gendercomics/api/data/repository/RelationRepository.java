package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RelationRepository extends MongoRepository<Relation, String> {

    @Query(value = "{ 'source.id': ?0 }")
    List<Relation> findSourceRelationByObjectId(String id);

    @Query(value = "{ 'target.id': ?0 }}")
    List<Relation> findTargetRelationByObjectId(String id);

}
