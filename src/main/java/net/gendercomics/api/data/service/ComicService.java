package net.gendercomics.api.data.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ComicService {

    private final ComicRepository _comicRepository;

    public List<Comic> findAll() {
        List<Comic> comics = _comicRepository.findAll();
        log.debug("#comics={}", comics.size());
        return comics;
    }

    public Comic findByTitle(String title) {
        return _comicRepository.findByTitle(title).orElse(null);
    }

    public boolean titleExists(String title) {
        return findByTitle(title) != null;
    }

    public Comic insert(Comic comic) {
        if (!titleExists(comic.getTitle())) {
            return _comicRepository.insert(comic);
        } else {
            log.warn("comic with title={} already exists", comic.getTitle());
        }
        return null;
    }

    public String getComicAsXml(String id) throws JsonProcessingException {
        Comic comic = _comicRepository.findById(id).orElse(null);
        if (comic != null) {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(comic);
        }
        return null;
    }

}
