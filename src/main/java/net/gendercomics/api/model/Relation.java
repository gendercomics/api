package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "relation model")
public class Relation {

    @ApiModelProperty(value = "the relation source object", required = true)
    private Object source;

    @ApiModelProperty(value = "the relation predicate", required = true)
    @NonNull
    private Predicate predicate;

    @ApiModelProperty(value = "the relation target object", required = true)
    private Object target;

}
