package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SearchService {

    private final MongoTemplate _mongoTemplate;

    public SearchResult search(String searchTerm) {

        Pattern regex = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);

        Query comicQuery = new Query();
        comicQuery.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(regex),
                Criteria.where("subTitle").regex(regex)
        ));

        List<Comic> comics = _mongoTemplate.find(comicQuery, Comic.class);
        if (comics != null) {
            return new SearchResult(comics);
        }

        return new SearchResult(Collections.emptyList());
    }

}
