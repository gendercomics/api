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

    public int removePublisher() {
        AtomicInteger count = new AtomicInteger();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getPublisher() != null)
                .forEach(comic -> {
                    comic.setPublisher(null);
                    _comicRepository.save(comic);
                    count.incrementAndGet();
                });

        return count.get();
    }

    public int removeCreatorRole() {
        AtomicInteger count = new AtomicInteger();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream().filter(comic -> comic.getCreators() != null)
                .forEach(comic -> {
                    comic.getCreators().stream().forEach(creator -> {
                        creator.setRole(null);
                        count.incrementAndGet();
                    });
                    _comicRepository.save(comic);
                });

        return count.get();
    }
}
