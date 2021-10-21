package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.repository.RelationRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MigrationResult;
import net.gendercomics.api.model.Relation;
import net.gendercomics.api.model.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MigrationService {

    private final ComicRepository _comicRepository;
    private final RelationRepository _relationRepository;
    private final ComicService _comicService;

    public MigrationResult comicCommentToRelation() {
        MigrationResult result = new MigrationResult();

        List<Comic> sourceList = _comicRepository.findWithComments();
        result.setSource(List.of(sourceList));

        List<Comic> resultList = new ArrayList<>();

        // find all comics with comments
        for (Comic comic : sourceList) {
            // get all comments
            for (Text text : comic.getComments()) {
                List<Relation> sourceRelation = _relationRepository.findRelationsBySourceId(text.getId());
                if (sourceRelation.isEmpty()) {
                    Relation relation = new Relation("comments", text.getId(), text.getClass().getName(), comic.getId(), comic.getClass().getName());
                    relation.setMetaData(text.getMetaData());
                    _relationRepository.insert(relation);
                }
                resultList.add(_comicService.getComic(comic.getId()));
            }
            result.setResult(Collections.singletonList(resultList));
        }

        return result;
    }

    public MigrationResult publisherToPublisherList() {
        MigrationResult result = new MigrationResult();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getPublisher() != null).forEach(comic -> {
            comic.setPublishers(new ArrayList<>());
            comic.getPublishers().add(comic.getPublisher());
            _comicRepository.save(comic);
        });

        result.setStatus(MigrationResult.OK);
        return result;
    }

    public MigrationResult roleToRoleList() {
        MigrationResult result = new MigrationResult();
        _comicRepository.findAll().stream().filter(comic -> comic.getCreators() != null).forEach(comic -> {
            comic.getCreators().forEach(creator -> {
                creator.setRoles(new ArrayList<>());
                creator.getRoles().add(creator.getRole());
            });
            _comicRepository.save(comic);
        });

        result.setStatus(MigrationResult.OK);
        return result;
    }

    public MigrationResult linkToLinkList() {
        MigrationResult result = new MigrationResult();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getHyperLink() != null).forEach(comic -> {
            comic.setHyperLinks((new ArrayList<>()));
            comic.getHyperLinks().add(comic.getHyperLink());
            _comicRepository.save(comic);
        });

        result.setStatus(MigrationResult.OK);
        return result;
    }

    public MigrationResult removeHyperLink() {
        MigrationResult result = new MigrationResult();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getHyperLink() != null).forEach(comic -> {
            comic.setHyperLink(null);
            _comicRepository.save(comic);
        });

        result.setStatus(MigrationResult.OK);
        return result;
    }

    public List<Comic> listComicsWithSeries() {
        return _comicRepository.findAll().stream().filter(comic -> comic.getSeries() != null).collect(Collectors.toList());
    }

    public MigrationResult seriesToSeriesList() {
        MigrationResult result = new MigrationResult();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getSeries() != null)
                .forEach(comic -> {
                    comic.setSeriesList(new ArrayList<>());
                    comic.getSeriesList().add(comic.getSeries());
                    _comicRepository.save(comic);
                });

        result.setStatus(MigrationResult.OK);
        return result;
    }

    public int removeSeries() {
        AtomicInteger count = new AtomicInteger();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getSeries() != null)
                .forEach(comic -> {
                    comic.setSeries(null);
                    _comicRepository.save(comic);
                    count.incrementAndGet();
                });

        return count.get();

    }
}
