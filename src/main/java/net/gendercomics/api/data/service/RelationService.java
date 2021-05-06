package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.data.repository.TextRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RelationService {

    private final RelationRepository _relationRepository;
    private final TextRepository _textRepository;
    private final ComicRepository _comicRepository;

    public Relation save(Relation relation, String userName) {
        if (relation.getMetaData() == null) {
            relation.setMetaData(new MetaData());
        }

        if (relation.getId() == null) {
            relation.getMetaData().setCreatedOn(new Date());
            relation.getMetaData().setCreatedBy(userName);
            return _relationRepository.insert(relation);
        } else {
            relation.getMetaData().setChangedOn(new Date());
            relation.getMetaData().setChangedBy(userName);
            return _relationRepository.save(relation);
        }
    }

    public void delete(String relationId) {
        _relationRepository.deleteById(relationId);
    }

    public List<Relation> findAllRelations(String id) {
        List<Relation> relations = _relationRepository.findRelationsBySourceId(id);
        relations.addAll(_relationRepository.findRelationsByTargetId(id));
        return relations;
    }

    public Map<String, List<Relation>> findAllRelationsGroupedByType(String id) {
        Map<String, List<Relation>> map = new HashMap<>();
        for (Relation relation : findAllRelations(id)) {
            map.computeIfAbsent(relation.getRelationType(), k -> new ArrayList<>())
                    .add(relation);
        }
        return map;
    }

    public List<Object> findAllRelationsForType(String relationType, String id) {
        List<Object> objects = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();

        // related object is target
        relations.addAll(_relationRepository.findRelationsByTypeAndSourceObjectId(relationType, id));
        // related object is source
        relations.addAll(_relationRepository.findRelationsByTypeAndTargetObjectId(relationType, id));
        // sort relations by created date
        Comparator<Relation> compareByCreatedDate = (Relation r1, Relation r2) ->
                r1.getMetaData().getCreatedOn().compareTo(r2.getMetaData().getCreatedOn());
        Collections.sort(relations, compareByCreatedDate);

        relations.forEach(relation -> {
            if (relation.getSourceId().equals(id)) {
                objects.add(getRelationSourceOrTargetById(relation.getTargetId(), relation.getTargetType()));
            }
            if (relation.getTargetId().equals(id)) {
                objects.add(getRelationSourceOrTargetById(relation.getSourceId(), relation.getSourceType()));
            }
        });

        return objects;
    }

    private Object getRelationSourceOrTargetById(String sourceId, String relationClassName) {
        switch (relationClassName) {
            case "net.gendercomics.api.model.Comic":
                return _comicRepository.findById(sourceId).get();
            case "net.gendercomics.api.model.Text":
                return _textRepository.findById(sourceId).get();
            default:
                throw new IllegalArgumentException("unsupported sourceType: " + relationClassName);
        }
    }

    public void deleteBySourceId(String sourceId) {
        List<Relation> relations = _relationRepository.findRelationsBySourceId(sourceId);
        if (relations.size() == 1) {
            _relationRepository.deleteById(relations.get(0).getId());
        } else {
            log.error("Cannot delete relation with sourceId=" + sourceId + ". Zero or more than one relation exists for this sourceId.");
        }

    }

    public boolean relationExists(String sourceId, String targetId) {
        return _relationRepository.findBySourceIdAndTargetId(sourceId, targetId) != null;
    }

}
