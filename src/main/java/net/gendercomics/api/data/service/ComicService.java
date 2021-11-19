package net.gendercomics.api.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ComicService {

    private final ComicRepository _comicRepository;
    private final RelationService _relationService;
    private final TextService _textService;

    public List<Comic> findAll() {
        List<Comic> comics = _comicRepository.findAll();
        log.debug("#comics={}", comics.size());
        Collections.sort(comics);
        return comics;
    }

    public List<Comic> findByTypes(ComicType... comicTypes) {
        List<Comic> comics = new ArrayList<>();
        for (ComicType comicType : comicTypes) {
            comics.addAll(_comicRepository.findByType(comicType));
        }
        Collections.sort(comics);
        return comics;
    }

    public List<Comic> findByTitle(String title) {
        return _comicRepository.findByTitle(title);
    }

    public boolean titleExists(String title) {
        return !_comicRepository.findByTitle(title).isEmpty();
    }

    public String getComicAsXml(String id) throws JsonProcessingException {
        Comic comic = _comicRepository.findById(id).orElse(null);
        if (comic != null) {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(comic);
        }
        return null;
    }

    public Comic getComic(String id) {
        return _comicRepository.findById(id).orElse(null);
    }

    public long getComicCount() {
        return _comicRepository.count();
    }

    public Comic save(Comic comic, String userName) {
        if (comic.getMetaData() == null) {
            comic.setMetaData(new MetaData());
        }

        // process publisher override
        comic.setPublisherOverrides(processPublisherLocationOverride(comic.getPublishers(), comic.getPublisherOverrides()));

        if (comic.getId() == null) {
            comic.getMetaData().setCreatedOn(new Date());
            comic.getMetaData().setCreatedBy(userName);
            return _comicRepository.insert(comic);
        } else {
            comic.getMetaData().setChangedOn(new Date());
            comic.getMetaData().setChangedBy(userName);
            return _comicRepository.save(comic);
        }
    }

    public void delete(String comicId) {
        _comicRepository.deleteById(comicId);
    }

    private Map<String, List<Relation>> loadRelations(String comicId) {
        return _relationService.findAllRelationsGroupedByType(comicId);
    }

    public List<Comic> findAllForList() {
        List<Comic> comics = _comicRepository.findAllLimitFields();
        Collections.sort(comics);
        return comics;
    }

    private Map<String, String> processPublisherLocationOverride(final List<Publisher> publishers, final Map<String, String> existingOverrides) {
        if (publishers == null || publishers.isEmpty()) {
            return null;
        }

        Map<String, String> overrides = new HashMap<>();

        publishers.stream().forEach(publisher -> {
            String locationOverride = publisher.getLocationOverride();
            if (locationOverride != null)
                overrides.put(publisher.getId(), publisher.getLocationOverride());
        });

        return overrides.isEmpty() ? null : overrides;
    }

}
