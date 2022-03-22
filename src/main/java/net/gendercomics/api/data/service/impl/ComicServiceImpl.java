package net.gendercomics.api.data.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.data.service.RelationService;
import net.gendercomics.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ComicServiceImpl implements ComicService {

    private final ComicRepository _comicRepository;
    private final RelationService _relationService;

    @Override
    public List<Comic> findAll() {
        List<Comic> comics = _comicRepository.findAll();
        Collections.sort(comics);
        return comics;
    }

    @Override
    public List<Comic> findByTypes(ComicType... comicTypes) {
        List<Comic> comics = new ArrayList<>();
        for (ComicType comicType : comicTypes) {
            comics.addAll(_comicRepository.findByType(comicType));
        }
        Collections.sort(comics);
        return comics;
    }

    @Override
    public List<Comic> findByTitle(String title) {
        return _comicRepository.findByTitle(title);
    }

    @Override
    public boolean titleExists(String title) {
        return !_comicRepository.findByTitle(title).isEmpty();
    }

    @Override
    public String getComicAsXml(String id) throws JsonProcessingException {
        Comic comic = _comicRepository.findById(id).orElse(null);
        if (comic != null) {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(comic);
        }
        return null;
    }

    @Override
    public Comic getComic(String id) {
        return _comicRepository.findById(id).orElse(null);
    }

    @Override
    public long getComicCount() {
        return _comicRepository.count();
    }

    @Override
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

    @Override
    public void delete(String comicId) {
        Comic comicToDelete = _comicRepository.findById(comicId).get();
        if (isSeries(comicToDelete)) {
            // if comic is series, delete related
            for (Comic comic : _comicRepository.getBySeriesId(comicId)) {
                Map<String, Series> seriesMap = comic.getSeriesAsMap();
                Series series = seriesMap.get(comicId);
                comic.getSeriesList().remove(series);
                _comicRepository.save(comic);
            }
        }
        // TODO if comicToDelete is anthology?
        _comicRepository.deleteById(comicId);
    }

    private boolean isSeries(Comic comic) {
        return ComicType.publishing_series.equals(comic.getType()) || ComicType.comic_series.equals(comic.getType());
    }

    @Override
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

    @Override
    public List<Comic> getByCreatorNames(List<Name> nameList) {
        Set<Comic> comicSet = new HashSet<>();

        nameList.stream().forEach(name -> {
            comicSet.addAll(_comicRepository.getByCreatorNameId(name.getId()));
        });

        return new ArrayList<>(comicSet);
    }

    @Override
    public List<Comic> getByPublisherNames(List<Publisher> publishers) {
        Set<Comic> comicSet = new HashSet<>();

        publishers.stream().forEach(publisher -> {
            comicSet.addAll(_comicRepository.getByPublisherId(publisher.getId()));
        });

        return new ArrayList<>(comicSet);
    }

    @Override
    public List<Comic> getBySeries(List<Comic> seriesList) {
        Set<Comic> comicSet = new HashSet<>();

        seriesList.stream().forEach(series -> {
            comicSet.addAll(_comicRepository.getBySeriesId(series.getId()));
        });

        return new ArrayList<>(comicSet);
    }

    @Override
    public Collection<? extends Comic> getByPartOf(List<Comic> partOfList) {
        Set<Comic> comicSet = new HashSet<>();

        partOfList.stream().forEach(partOf -> {
            comicSet.addAll(_comicRepository.getByPartOf(partOf.getId()));
        });

        return comicSet;
    }

}
