package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SearchService {

    private final MongoTemplate _mongoTemplate;

    public SearchResult search(String searchTerm) {
        TextCriteria criteria = TextCriteria.forLanguage("de").matching(searchTerm);
        Query query = TextQuery.queryText(criteria).sortByScore();

        List<Comic> comics = _mongoTemplate.find(query, Comic.class);

        if (comics != null) {
            return new SearchResult(comics);
        }

        return new SearchResult(Collections.emptyList());
    }

}
