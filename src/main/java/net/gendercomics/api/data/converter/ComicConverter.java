package net.gendercomics.api.data.converter;

import net.gendercomics.api.elastic.model.ElasticComic;
import net.gendercomics.api.model.Comic;
import org.springframework.stereotype.Service;

@Service
public class ComicConverter extends Converter<ElasticComic, Comic> {

    public ComicConverter() {
        super(ComicConverter::convertToMongo, ComicConverter::convertToElastic);
    }

    private static ElasticComic convertToElastic(Comic comic) {
        return new ElasticComic(comic.getId(), comic.getTitle(), comic.getSubTitle(), comic.getType());
    }

    private static Comic convertToMongo(ElasticComic elasticComic) {
        Comic comic = new Comic();
        comic.setId(elasticComic.getId());
        //comic.setMetaData(comic.getMetaData());
        comic.setTitle(elasticComic.getTitle());
        comic.setSubTitle(elasticComic.getSubTitle());
        comic.setType(elasticComic.getType());

        return comic;
    }

}
