package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "relation model")
public class Relation {

    @ApiModelProperty(value = "the relation predicate", required = true)
    @NonNull
    private Predicate predicate;

    @ApiModelProperty(value = "the relation target object id", required = true)
    private Object target;

}
