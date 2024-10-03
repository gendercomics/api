package net.gendercomics.api.data.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.KeywordRepository;
import net.gendercomics.api.data.repository.PredicateRepository;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.KeywordType;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.RelationIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository _keywordRepository;
    private final PredicateRepository _predicateRepository;

    public List<Keyword> findAll() {
        return _keywordRepository.findAll();
    }

    public List<Keyword> findByType(String type) {
        return _keywordRepository.findByType(KeywordType.valueOf(type));
    }

    public Keyword getKeyword(String id) {
        Keyword keyword = _keywordRepository.findById(id).orElse(null);
        keyword.setRelations(loadRelations(keyword.getRelationIds()));
        return keyword;
    }

    public Keyword save(Keyword keyword, String userName) {
        List<RelationIds> storedRelationIds = Collections.emptyList();
        if (keyword.getId() != null) {
            storedRelationIds = _keywordRepository.findById(keyword.getId()).get().getRelationIds();
        }

        if (keyword.getMetaData().getCreatedOn() == null) {
            keyword.getMetaData().setCreatedOn(new Date());
            keyword.getMetaData().setCreatedBy(userName);
            keyword = _keywordRepository.insert(keyword);

            if (!isRelationIdsEmpty(keyword.getRelationIds())) {
                // set relation ids of source or target
                Keyword finalKeyword = keyword;
                keyword.getRelationIds().stream().filter(relationId -> relationId.getSourceId() == null).forEach(relationId -> relationId.setSourceId(finalKeyword.getId()));
                keyword.getRelationIds().stream().filter(relationId -> relationId.getTargetId() == null).forEach(relationId -> relationId.setTargetId(finalKeyword.getId()));
            }
        }
        keyword.getMetaData().setChangedOn(new Date());
        keyword.getMetaData().setChangedBy(userName);

        keyword = _keywordRepository.save(keyword);
        this.updateRelationsInRelatedObjects(keyword.getId(), keyword.getRelationIds(), storedRelationIds);

        keyword.setRelations(loadRelations(keyword.getRelationIds()));

        return keyword;
    }

    private void updateRelationsInRelatedObjects(@NonNull String id, List<RelationIds> relationIds, List<RelationIds> storedRelationIds) {
        removeRelatedKeyword(id, relationIds, storedRelationIds);
        addRelatedKeyword(id, relationIds, storedRelationIds);
    }

    private void removeRelatedKeyword(String id, List<RelationIds> relationIds, List<RelationIds> storedRelationIds) {
        if (!isRelationIdsEmpty(storedRelationIds)) {
            // was a relation deleted or updated?
            storedRelationIds.forEach(sRId -> {
                if (!relationIds.contains(sRId)) {
                    Keyword kw = null;
                    if (sRId.getSourceId().equals(id)) {
                        kw = this.getKeyword(sRId.getTargetId());
                    } else if (sRId.getTargetId().equals(id)) {
                        kw = this.getKeyword(sRId.getSourceId());
                    }
                    kw.removeRelationIds(sRId);
                    _keywordRepository.save(kw);
                }
            });
        }
    }

    private void addRelatedKeyword(String id, List<RelationIds> relationIds, List<RelationIds> storedRelationIds) {
        if (!isRelationIdsEmpty(relationIds)) {
            relationIds.forEach(rIds -> {
                if (!storedRelationIds.contains(rIds)) {
                    Keyword kw = null;
                    if (rIds.getSourceId().equals(id)) {
                        kw = this.getKeyword(rIds.getTargetId());
                    } else if (rIds.getTargetId().equals(id)) {
                        kw = this.getKeyword(rIds.getSourceId());
                    }
                    kw.addRelationIds(rIds);
                    _keywordRepository.save(kw);
                }
            });
        }
    }

    private List<Relation> loadRelations(List<RelationIds> relationIds) {
        if (isRelationIdsEmpty(relationIds)) {
            return null;
        }

        // TODO check if ids are present in relationIds - make the statement more robust or fault tolerant

        relationIds.forEach(relationId -> {
                    _keywordRepository.findById(relationId.getSourceId()).ifPresent(sourceKeyword -> {
                    });
                    _keywordRepository.findById(relationId.getTargetId()).ifPresent(targetKeyword -> {
                    });
                }
        );

        return relationIds.stream()
                .map(relationId -> new Relation(
                        _keywordRepository.findById(relationId.getSourceId()).get(),
                        _predicateRepository.findById(relationId.getPredicateId()).orElse(null),
                        _keywordRepository.findById(relationId.getTargetId()).get()))
                .collect(Collectors.toList());
    }

    private boolean isRelationIdsEmpty(List<RelationIds> relationIds) {
        return relationIds == null || relationIds.isEmpty();
    }

    public long getKeywordCount() {
        return _keywordRepository.count();
    }

    public void delete(String id) {
        // TODO find RelationIds where Keyword is used -> better: method to find relation ids, ask user, delete on confirmation
        List<Keyword> keywords = getUsedInRelation(id);
        deleteRelationIdsInRelatedKeywords(id, keywords);
        // TODO delete relation
        _keywordRepository.deleteById(id);
    }

    private List<Keyword> getUsedInRelation(final String id) {
        // TODO find all Relations where the id is used in RelationIds
        return Collections.emptyList();
    }

    private void deleteRelationIdsInRelatedKeywords(String id, List<Keyword> keywords) {
        // TODO implement method
    }
}
