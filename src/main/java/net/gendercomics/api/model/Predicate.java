package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@Setter
@Document("predicates")
public class Predicate implements DisplayNameI18n {

    private String id;

    @ApiModelProperty(value = "list of predicate values (one list entry per language)", required = true)
    private Map<Language, String> values;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Override
    public Map<Language, String> getDisplayNames() {
        return getValues();
    }
}
