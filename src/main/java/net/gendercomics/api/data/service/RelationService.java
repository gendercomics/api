package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.MetaData;
import net.gendercomics.api.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RelationService {

    private final RelationRepository _relationRepository;

    public Relation save(Relation relation, String userName) {
        if (relationExists(relation.getSourceId(), relation.getTargetId())) {
            relation = _relationRepository.findBySourceIdAndTargetId(relation.getSourceId(), relation.getTargetId());
            relation.getMetaData().setChangedOn(new Date());
            relation.getMetaData().setChangedBy(userName);
            return _relationRepository.save(relation);
        }

        relation.setMetaData(new MetaData());
        relation.getMetaData().setCreatedOn(new Date());
        relation.getMetaData().setCreatedBy(userName);
        return _relationRepository.insert(relation);
    }

    public void delete(String relationId) {
        _relationRepository.deleteById(relationId);
    }

    public List<Relation> findAllRelations(String id) {
        List<Relation> relations = _relationRepository.findRelationsBySourceId(id);
        relations.addAll(_relationRepository.findRelationsByTargetId(id));
        return relations;
    }


    public boolean relationExists(String sourceId, String targetId) {
        return _relationRepository.findBySourceIdAndTargetId(sourceId, targetId) != null;
    }

}
