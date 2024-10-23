package net.gendercomics.api.data.service;

import net.gendercomics.api.model.Keyword;
import net.gendercomics.api.model.Language;

import java.util.List;

public interface KeywordService {

    List<Keyword> findAll();

    List<Keyword> findByType(String type);

    List<Keyword> findBySearchTerm(String searchTerm, Language language);

    Keyword getKeyword(String id);

    Keyword save(Keyword keyword, String userName);

    long getKeywordCount();

    void delete(String id);
}
