package net.gendercomics.api.data.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ComicService {

    private final ComicRepository _comicRepository;

    public List<Comic> findAll() {
        List<Comic> comics = _comicRepository.findAll();
        log.debug("#comics={}", comics.size());
        Collections.sort(comics);
        return comics;
    }

    public Comic findByTitle(String title) {
        return _comicRepository.findByTitle(title).orElse(null);
    }

    public Comic insert(Comic comic, String userName) {
        log.debug("userName={} tries to insert comic", userName);
        comic.setMetaData(new MetaData());
        comic.getMetaData().setCreatedOn(new Date());
        comic.getMetaData().setCreatedBy(userName);
        return _comicRepository.insert(comic);
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
        comic.getMetaData().setChangedOn(new Date());
        comic.getMetaData().setChangedBy(userName);
        return _comicRepository.save(comic);
    }
}
