package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.gendercomics.api.model.jackson.KeywordDeserializer;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "keywords")
@ApiModel(description = "keyword")
@JsonDeserialize(using = KeywordDeserializer.class)
public class Keyword implements DisplayNameI18n {

    @NonNull
    private String id;

    @NonNull
    @ApiModelProperty(value = "metadata")
    private MetaData metaData;

    @NonNull
    @ApiModelProperty(value = "keyword type (content)", required = true)
    private KeywordType type;

    @NonNull
    @ApiModelProperty(value = "list of keywords (one list entry per language)", required = true)
    private Map<Language, KeywordValue> values;

    @ApiModelProperty(value = "list of relations")
    @Transient
    private List<Relation> relations;

    @ApiModelProperty(hidden = true)
    private List<RelationIds> relationIds;

    public Keyword(String id, MetaData metaData, KeywordType type, Map<Language, KeywordValue> values, List<RelationIds> relationIds) {
        this.id = id;
        this.metaData = metaData;
        this.type = type;
        this.values = values;
        this.relationIds = relationIds;
    }

    @Override
    public Map<Language, String> getDisplayNames() {
        Map<Language, String> map = getValues().values().stream()
                .collect(Collectors.toMap(KeywordValue::getLanguage, KeywordValue::getName, (a, b) -> b));
        return map;
    }
}
