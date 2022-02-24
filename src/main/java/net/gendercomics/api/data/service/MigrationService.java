package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MigrationService {

    private final ComicRepository _comicRepository;

    public MigrationResult listEmptyHyperlink() {
        MigrationResult migrationResult = new MigrationResult();

        List<Comic> source = new ArrayList<>();

        List<Comic> comicList = _comicRepository.findAll();
        comicList.stream()
                .filter(comic -> comic.getHyperLinks() != null)
                .forEach(comic -> {
                    comic.getHyperLinks().stream()
                            .filter(hyperLink -> hyperLink.getUrl() == null)
                            .forEach(hyperLink -> {
                                log.info("comic with empty hyperlinks: " + comic.getTitle());
                                source.add(comic);
                            });
                });
        migrationResult.setSource(Collections.singletonList(source));
        migrationResult.setStatus(MigrationResult.OK);
        return migrationResult;
    }

    public MigrationResult removeEmptyHyperlink() {
        MigrationResult migrationResult = listEmptyHyperlink();
        List<Comic> result = new ArrayList<>();
        for (Object o : migrationResult.getSource()) {
            Comic comic = (Comic) o;
            comic.setHyperLinks(null);
            _comicRepository.save(comic);
            result.add(comic);
        }
        migrationResult.setResult(Collections.singletonList(result));

        return migrationResult;
    }

}
