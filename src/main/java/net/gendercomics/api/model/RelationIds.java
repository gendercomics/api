package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode()
public class RelationIds {

    @ApiModelProperty(value = "the relation target object id", required = true)
    private String sourceId;

    @ApiModelProperty(value = "the relation predicate object id", required = true)
    private String predicateId;

    @ApiModelProperty(value = "the relation target object id", required = true)
    private String targetId;

}
