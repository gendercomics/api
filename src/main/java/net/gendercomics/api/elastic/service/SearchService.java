package net.gendercomics.api.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.converter.ComicConverter;
import net.gendercomics.api.elastic.repository.ElasticComicRepository;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SearchService {

    private final ElasticComicRepository _comicRepository;
    private final ComicConverter _converter;

    public List<Comic> search(String searchTerm) {
        List<Comic> comics = _converter.createFromElasticList(_comicRepository.findAllByTitleLike(searchTerm));
        Collections.sort(comics);
        return comics;
    }

}
