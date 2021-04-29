package net.gendercomics.api.data.repository;

import net.gendercomics.api.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RelationRepository extends MongoRepository<Relation, String> {

    @Query(value = "{ 'sourceId': ?0 }", fields = "{ 'id':1, 'sourceId': 1, 'targetId': 1, 'target': 1, 'attributes': 1, 'metaData': 1 }")
    List<Relation> findRelationsBySourceObjectId(String id);

    @Query(value = "{ 'targetId': ?0 }}", fields = "{ 'id':1, 'sourceId': 1, 'source': 1, 'targetId': 1, 'attributes': 1, 'metaData': 1 }")
    List<Relation> findRelationsByTargetObjectId(String id);

    @Query(value = "{ 'attributes.relationType': ?0, 'sourceId': ?1 }}", fields = "{ 'id':1, 'sourceId': 1, 'targetId': 1, 'target': 1, 'attributes': 1, 'metaData': 1 }")
    List<Relation> findRelationsByTypeAndSourceObjectId(String relationType, String id);

    @Query(value = "{ 'attributes.relationType': ?0, 'targetId': ?1 }}", fields = "{ 'id':1, 'sourceId': 1, 'source': 1, 'targetId': 1, 'attributes': 1, 'metaData': 1 }")
    List<Relation> findRelationsByTypeAndTargetObjectId(String relationType, String id);
}
