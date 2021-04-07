package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "comments")
@ApiModel(description = "comment model")
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "relation type", required = true)
    private RelationType relationType;

    @ApiModelProperty(value = "the relation source", required = true)
    @DBRef
    private Object source;

    @ApiModelProperty(value = "the relation target", required = true)
    @DBRef
    private Object target;
}
