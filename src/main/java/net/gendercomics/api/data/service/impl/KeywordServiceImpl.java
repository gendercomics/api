package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.KeywordRepository;
import net.gendercomics.api.data.repository.PredicateRepository;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (keyword.getMetaData() == null) {
            keyword.setMetaData(new MetaData());
        }
        if (keyword.getMetaData().getCreatedOn() == null) {
            keyword.getMetaData().setCreatedOn(new Date());
            keyword.getMetaData().setCreatedBy(userName);
            return _keywordRepository.insert(keyword);
        }
        keyword.getMetaData().setChangedOn(new Date());
        keyword.getMetaData().setChangedBy(userName);

        return _keywordRepository.save(keyword);
    }

    private List<Relation> loadRelations(List<RelationIds> relationIds) {
        return relationIds.stream()
                .map(relationId -> new Relation(_predicateRepository.findById(relationId.getPredicateId()).orElse(null),
                        _keywordRepository.findById(relationId.getTargetId()).get()))
                .collect(Collectors.toList());
    }

    public long getKeywordCount() {
        return _keywordRepository.count();
    }

    public void delete(String id) {
        _keywordRepository.deleteById(id);
    }
}
