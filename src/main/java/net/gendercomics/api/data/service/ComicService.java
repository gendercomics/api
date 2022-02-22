package net.gendercomics.api.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import net.gendercomics.api.model.Name;
import net.gendercomics.api.model.Publisher;

import java.util.Collection;
import java.util.List;

public interface ComicService {
    List<Comic> findAll();

    List<Comic> findByTypes(ComicType... comicTypes);

    List<Comic> findByTitle(String title);

    boolean titleExists(String title);

    String getComicAsXml(String id) throws JsonProcessingException;

    Comic getComic(String id);

    long getComicCount();

    Comic save(Comic comic, String userName);

    void delete(String comicId);

    List<Comic> findAllForList();

    List<Comic> getByCreatorNames(List<Name> nameList);

    List<Comic> getByPublisherNames(List<Publisher> publishers);

    List<Comic> getBySeries(List<Comic> seriesList);

    Collection<? extends Comic> getByPartOf(List<Comic> partOfList);

}
