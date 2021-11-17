package net.gendercomics.api.elastic.repository;

import net.gendercomics.api.elastic.model.ElasticComic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticComicRepository extends ElasticsearchRepository<ElasticComic, String> {

    List<ElasticComic> findAllByTitleLike(String searchTerm);

}
