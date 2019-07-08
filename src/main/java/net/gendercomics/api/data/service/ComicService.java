package net.gendercomics.api.data.service;

import java.util.List;

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

    /*
    public String getComicAsXml(String id) throws JsonProcessingException {
        Optional<Comic> comic = _comicRepository.findById(id);

        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(comic.get());
    }
     */

}
