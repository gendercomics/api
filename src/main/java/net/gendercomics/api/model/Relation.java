package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@Document(collection = "relations")
@ApiModel(description = "relation model")
@CompoundIndexes(value = {
        @CompoundIndex(name = "relation_ids_index", def = "{'sourceId':1, 'targetId':1}", unique = true, sparse = true)
})
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "the relation source object id", required = true)
    @Indexed
    private String sourceId;

    @ApiModelProperty(value = "the relation source type", required = true)
    private String sourceType;

    @ApiModelProperty(value = "the relation target object id", required = true)
    @Indexed
    private String targetId;

    @ApiModelProperty(value = "the relation target type", required = true)
    private String targetType;

    @NonNull
    private Map<String, String> attributes;

    private Relation() {
    }

    public Relation(@NonNull String relationType, @NonNull String sourceId, @NonNull String sourceType, @NonNull String targetId, @NonNull String targetType) {
        this.attributes = new HashMap<>();
        this.attributes.put("relationType", Objects.requireNonNull(relationType, "relationType must not be null"));
        this.sourceId = Objects.requireNonNull(sourceId, "sourceId must not be null");
        this.sourceType = Objects.requireNonNull(sourceType);
        this.targetId = Objects.requireNonNull(targetId, "targetId must not be null");
        this.targetType = Objects.requireNonNull(targetType);
    }

    @Transient
    @JsonIgnore
    public String getRelationType() {
        return this.attributes.get("relationType");
    }

}
