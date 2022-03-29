package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.KeywordRepository;
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

    public List<Keyword> findAll() {
        return _keywordRepository.findAll();
    }

    public List<Keyword> findByType(String type) {
        return _keywordRepository.findByType(KeywordType.valueOf(type));
    }

    public Keyword getKeyword(String id) {
        return _keywordRepository.findById(id).orElse(null);
    }

    public Keyword save(Keyword keyword, String userName) {
        if (keyword.getMetaData() == null) {
            keyword.setMetaData(new MetaData());
            keyword.getMetaData().setCreatedOn(new Date());
            keyword.getMetaData().setCreatedBy(userName);
            return _keywordRepository.insert(keyword);
        }
        keyword.getMetaData().setChangedOn(new Date());
        keyword.getMetaData().setChangedBy(userName);

        keyword.setRelationIds(this.processRelations(keyword.getRelations()));

        return _keywordRepository.save(keyword);
    }

    private List<RelationIds> processRelations(List<Relation> relations) {
        if (relations == null || relations.isEmpty()) {
            return null;
        }
        return relations.stream()
                .map(relation -> new RelationIds(relation.getPredicate().getId(), ((Keyword) relation.getTarget()).getId())).collect(Collectors.toList());
    }

    public long getKeywordCount() {
        return _keywordRepository.count();
    }

    public void delete(String id) {
        _keywordRepository.deleteById(id);
    }
}
