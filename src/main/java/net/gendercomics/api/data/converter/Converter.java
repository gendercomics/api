package net.gendercomics.api.data.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<T, U> {

    private final Function<T, U> fromElastic;
    private final Function<U, T> fromMongo;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromElastic = fromDto;
        this.fromMongo = fromEntity;
    }

    public final U convertFromElastic(final T elastic) {
        return fromElastic.apply(elastic);
    }

    public final T convertFromMongo(final U mongo) {
        return fromMongo.apply(mongo);
    }

    public final List<U> createFromElasticList(final Collection<T> elasticList) {
        return elasticList.stream().map(this::convertFromElastic).collect(Collectors.toList());
    }

    public final List<T> createFromMongoList(final Collection<U> mongoList) {
        return mongoList.stream().map(this::convertFromMongo).collect(Collectors.toList());
    }
}
