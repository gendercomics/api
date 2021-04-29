package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@Document(collection = "relations")
@ApiModel(description = "relation model")
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "the relation source object id", required = true)
    private String sourceId;

    @ApiModelProperty(value = "the relation source", required = true)
    private Object source;

    @ApiModelProperty(value = "the relation target object id", required = true)
    private String targetId;

    @ApiModelProperty(value = "the relation target", required = true)
    private Object target;

    @NonNull
    private Map<String, String> attributes;

    private Relation() {
    }

    public Relation(@NonNull String relationType, @NonNull String sourceId, @NonNull Object source, @NonNull String targetId, @NonNull Object target) {
        this.attributes = new HashMap<>();
        this.attributes.put("relationType", Objects.requireNonNull(relationType, "relationType must not be null"));
        this.sourceId = Objects.requireNonNull(sourceId, "sourceId must not be null");
        this.source = Objects.requireNonNull(source, "source must not be null");
        this.targetId = Objects.requireNonNull(targetId, "targetId must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
    }

    @Transient
    @JsonIgnore
    public String getRelationType() {
        return this.attributes.get("relationType");
    }
}
