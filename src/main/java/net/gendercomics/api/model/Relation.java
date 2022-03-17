package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document(collection = "relations")
@ApiModel(description = "relation model")
@CompoundIndexes(value = {
        @CompoundIndex(name = "relation_triple_index", def = "{'sourceId':1, 'predicate.id':1, 'targetId':1}", unique = true, sparse = true)
})
public class Relation {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "the relation source object id", required = true)
    @Indexed
    @NonNull
    private String sourceId;

    @ApiModelProperty(value = "the relation target object id", required = true)
    @Indexed
    @NonNull
    private String targetId;

    @ApiModelProperty(value = "the relation predicate", required = true)
    @NonNull
    private Predicate predicate;

}
