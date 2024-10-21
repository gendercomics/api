package net.gendercomics.api.data.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.data.service.SearchService;
import net.gendercomics.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        List<Comic> comicList = findComics(regex);
        Set<Comic> comicSet = new HashSet<>(comicList);

        // find all comics in a series found by previous search
        List<Comic> seriesList = comicList.stream()
                .filter(comic -> ComicType.comic_series.equals(comic.getType()) || ComicType.publishing_series.equals(comic.getType()))
                .collect(Collectors.toList());
        comicSet.addAll(_comicService.getBySeries(seriesList));

        // find all comics within an anthology or magazine
        List<Comic> anthologyList = comicList.stream()
                .filter(comic -> ComicType.anthology.equals(comic.getType()) || ComicType.magazine.equals(comic.getType()))
                .collect(Collectors.toList());
        comicSet.addAll(_comicService.getByPartOf(anthologyList));

        // search names, add comic results
        result.setNames(findNames(searchTerm));
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

    private List<Name> findNames(String searchTerm) {
        List<Name> names = new ArrayList<>();
        Query creatorQuery = new Query();
        String[] split = searchTerm.trim().split("\\s+");


        if (split.length > 1) {
            creatorQuery.addCriteria(new Criteria().andOperator(
                    Criteria.where("firstName").regex(Pattern.compile(split[0], Pattern.CASE_INSENSITIVE)),
                    Criteria.where("lastName").regex(Pattern.compile(split[1], Pattern.CASE_INSENSITIVE)),
                    Criteria.where("searchable").is(true)
            ));

            names.addAll(_mongoTemplate.find(creatorQuery, Name.class));
        } else {
            creatorQuery.addCriteria(new Criteria().orOperator(
                    Criteria.where("firstName").regex(Pattern.compile(split[0], Pattern.CASE_INSENSITIVE)),
                    Criteria.where("lastName").regex(Pattern.compile(split[0], Pattern.CASE_INSENSITIVE))
            ));

            names.addAll(_mongoTemplate.find(creatorQuery, Name.class));
        }

        creatorQuery = new Query();
        creatorQuery.addCriteria(new Criteria().andOperator(
                Criteria.where("name").regex(Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE)),
                Criteria.where("searchable").is(true)
        ));

        names.addAll(_mongoTemplate.find(creatorQuery, Name.class));

        return names;
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
