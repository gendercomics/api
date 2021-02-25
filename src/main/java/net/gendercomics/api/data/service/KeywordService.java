package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.KeywordRepository;
import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.KeywordType;
import net.gendercomics.api.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class KeywordService {

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

    public Keyword insert(Keyword keyword, String userName) {
        keyword.setMetaData(new MetaData());
        keyword.getMetaData().setCreatedOn(new Date());
        keyword.getMetaData().setCreatedBy(userName);
        return _keywordRepository.insert(keyword);
    }

    public Keyword save(Keyword keyword, String userName) {
        if (keyword.getMetaData() == null) {
            keyword.setMetaData(new MetaData());
        }
        keyword.getMetaData().setChangedOn(new Date());
        keyword.getMetaData().setChangedBy(userName);
        return _keywordRepository.save(keyword);
    }

    public long getKeywordCount() {
        return _keywordRepository.count();
    }

    public void delete(String id) {
        _keywordRepository.deleteById(id);
    }
}
