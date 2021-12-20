package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.data.service.SearchService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.Name;
import net.gendercomics.api.model.Publisher;
import net.gendercomics.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final MongoTemplate _mongoTemplate;
    private final ComicService _comicService;

    @Override
    public SearchResult search(String searchTerm) {
        SearchResult result = new SearchResult();
        Pattern regex = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);

        // search comics
        Set<Comic> comicSet = new HashSet<>(findComics(regex));

        // search names, add comic results
        result.setNames(findNames(regex));
        comicSet.addAll(findComicsByNames(result.getNames()));

        // search publisher, add comic results
        result.setPublishers(findPublishers(regex));
        comicSet.addAll(findComicsByPublishers(result.getPublishers()));

        if (!comicSet.isEmpty()) {
            ArrayList<Comic> comics = new ArrayList<>(comicSet);
            Collections.sort(comics);
            result.setComics(comics);
        }

        return result;
    }

    private List<Comic> findComics(Pattern regex) {
        Query comicQuery = new Query();
        comicQuery.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(regex),
                Criteria.where("subTitle").regex(regex)
        ));

        return _mongoTemplate.find(comicQuery, Comic.class);
    }

    private List<Comic> findComicsByNames(List<Name> nameList) {
        return _comicService.getByCreatorNames(nameList);
    }

    private List<Name> findNames(Pattern regex) {
        Query creatorQuery = new Query();
        creatorQuery.addCriteria(new Criteria().orOperator(
                Criteria.where("firstName").regex(regex),
                Criteria.where("lastName").regex(regex)
        ));

        return _mongoTemplate.find(creatorQuery, Name.class);
    }

    private List<Comic> findComicsByPublishers(List<Publisher> publishers) {
        return _comicService.getByPublisherNames(publishers);
    }

    private List<Publisher> findPublishers(Pattern regex) {
        Query publisherQuery = new Query();
        publisherQuery.addCriteria(new Criteria().orOperator(
                Criteria.where("name").regex(regex)
        ));

        return _mongoTemplate.find(publisherQuery, Publisher.class);
    }

    @Override
    public List<Comic> searchAndReturnComics(String searchTerm) {
        return search(searchTerm).getComics();
    }

}
