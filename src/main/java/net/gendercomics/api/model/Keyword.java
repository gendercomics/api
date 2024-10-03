package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.gendercomics.api.model.jackson.KeywordDeserializer;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
public class Keyword implements DisplayNameI18n, Comparable<Keyword> {

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

    @ApiModelProperty(hidden = true)
    @Transient
    private Language currentLanguage = Language.de;

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

    @Transient
    @JsonIgnore
    public void addRelationIds(RelationIds relationIds) {
        if (this.relationIds == null) {
            this.relationIds = new ArrayList<>();
        }
        if (!this.relationIds.contains(relationIds)) {
            this.relationIds.add(relationIds);
        }
    }

    @Transient
    @JsonIgnore
    public void removeRelationIds(RelationIds relationIds) {
        if (this.relationIds != null) {
            this.relationIds.remove(relationIds);
        }
    }

    @Transient
    public int getRelationsOut() {
        if (this.relationIds != null) {
            return Math.toIntExact(this.relationIds.stream().filter(relationIds -> relationIds.getSourceId().equals(this.id)).count());
        }
        return 0;
    }

    @Transient
    public int getRelationsIn() {
        if (this.relationIds != null) {
            return Math.toIntExact(this.relationIds.stream().filter(relationIds -> relationIds.getTargetId().equals(this.id)).count());
        }
        return 0;
    }

    @Override
    public int compareTo(Keyword o) {
        return this.getValues().get(getCurrentLanguage()).getName().compareToIgnoreCase(o.getValues().get(getCurrentLanguage()).getName());
    }
}
