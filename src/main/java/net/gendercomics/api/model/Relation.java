package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "relation")
@ApiModel(description = "relation model")
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "relation type", required = true)
    private RelationType relationType;

    @ApiModelProperty(value = "the relation source's id", required = true)
    private ObjectId sourceId;

    @ApiModelProperty(value = "the relation source", required = true)
    @DBRef(lazy = true)
    private Object source;

    @ApiModelProperty(value = "the relation source's id", required = true)
    private ObjectId targetId;

    @ApiModelProperty(value = "the relation target", required = true)
    @DBRef(lazy = true)
    private Object target;
}
