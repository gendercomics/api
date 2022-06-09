package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Document("predicates")
public class Predicate implements DisplayNameI18n, Comparable<Predicate> {

    private String id;

    @ApiModelProperty(value = "list of predicate values (one list entry per language)", required = true)
    private Map<Language, String> values;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Override
    public Map<Language, String> getDisplayNames() {
        return getValues();
    }

    @Override
    public int compareTo(Predicate o) {
        return this.getValues().get(Language.de).compareTo(o.getValues().get(Language.de));
    }
}
