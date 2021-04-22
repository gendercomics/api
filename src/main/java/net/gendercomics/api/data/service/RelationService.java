package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.RelationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RelationService {

    private final RelationRepository _relationRepository;

    public List<Relation> findAllRelations(String id) {
        List<Relation> relations = _relationRepository.findSourceRelationByObjectId(id);
        relations.addAll(_relationRepository.findTargetRelationByObjectId(id));
        return relations;
    }

    public Map<RelationType, List<Relation>> findAllRelationsGroupedByType(String id) {
        Map<RelationType, List<Relation>> map = new HashMap<>();
        for (Relation relation : findAllRelations(id)) {
            map.computeIfAbsent(relation.getRelationType(), k -> new ArrayList<>())
                    .add(relation);
        }
        return map;
    }

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
}
