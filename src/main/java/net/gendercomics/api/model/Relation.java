package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "relations")
@ApiModel(description = "relation model")
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "the relation source", required = true)
    private RelationId source;

    @ApiModelProperty(value = "the relation target", required = true)
    private RelationId target;

    @NonNull
    private Map<String, String> attributes;

    private Relation() {
    }

    public Relation(String relationType, RelationId source, RelationId target) {
        this.attributes = new HashMap<>();
        this.attributes.put("relationType", relationType);
        this.source = source;
        this.target = target;
    }

    public String getRelationType() {
        return this.attributes.get("relationType");
    }
}
