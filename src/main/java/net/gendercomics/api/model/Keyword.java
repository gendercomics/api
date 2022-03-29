package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Document(collection = "keywords")
@ApiModel(description = "keyword")
public class Keyword implements DisplayNameI18n {

    private String id;

    @ApiModelProperty(value = "metadata")
    private MetaData metaData;

    @ApiModelProperty(value = "keyword type (content)", required = true)
    private KeywordType type;

    @ApiModelProperty(value = "list of keywords (one list entry per language)", required = true)
    private Map<Language, KeywordValue> values;

    @ApiModelProperty(value = "list of relations")
    @Transient
    private List<Relation> relations;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<RelationIds> relationIds;

    @Override
    public Map<Language, String> getDisplayNames() {
        Map<Language, String> map = getValues().values().stream()
                .collect(Collectors.toMap(KeywordValue::getLanguage, KeywordValue::getName, (a, b) -> b));
        return map;
    }
}
