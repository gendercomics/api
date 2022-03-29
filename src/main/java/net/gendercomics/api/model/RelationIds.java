package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@CompoundIndexes(value = {
        @CompoundIndex(name = "relation_ids_index", def = "{'predicateId':1, 'targetId':1}", unique = true, sparse = true)
})
@Data
@AllArgsConstructor
public class RelationIds {

    @ApiModelProperty(value = "the relation predicate object id", required = true)
    private String predicateId;

    @ApiModelProperty(value = "the relation target object id", required = true)
    private String targetId;

}
