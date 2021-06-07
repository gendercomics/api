package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RelationRepository extends MongoRepository<Relation, String> {

    @Query(value = "{ 'sourceId': ?0 }")
    List<Relation> findRelationsBySourceId(String id);

    @Query(value = "{ 'targetId': ?0 }}")
    List<Relation> findRelationsByTargetId(String id);

    @Query(value = "{ 'attributes.relationType': ?0, 'sourceId': ?1 }}")
    List<Relation> findRelationsByTypeAndSourceObjectId(String relationType, String id);

    @Query(value = "{ 'attributes.relationType': ?0, 'targetId': ?1 }}")
    List<Relation> findRelationsByTypeAndTargetObjectId(String relationType, String id);

    @Query(value = "{ 'sourceId': ?0, 'targetId': ?1 }")
    Relation findBySourceIdAndTargetId(String sourceId, String targetId);
}
