package net.gendercomics.api.data.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.KeywordRepository;
import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class KeywordService {

    private final KeywordRepository _keywordRepository;

    public List<Keyword> findAll() {
        List<Keyword> keywordList = _keywordRepository.findAll();
        Collections.sort(keywordList);
        return keywordList;
    }

    public Keyword getKeyword(String id) {
        // TODO implement method
        log.warn("not implemented yet");
        return null;
    }

    public Keyword insert(Keyword keyword, String userName) {
        keyword.setMetaData(new MetaData());
        keyword.getMetaData().setCreatedOn(new Date());
        keyword.getMetaData().setCreatedBy(userName);
        return _keywordRepository.insert(keyword);
    }

    public Keyword save(Keyword keyword, String userName) {
        // TODO implement method
        log.warn("not implemented yet");
        return null;
    }
}
