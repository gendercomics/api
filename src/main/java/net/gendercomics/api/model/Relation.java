package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@ApiModel(description = "relation model")
public class Relation {

    @ApiModelProperty(value = "the relation target object id", required = true)
    @JsonIgnore
    private Object target;

    @ApiModelProperty(value = "the relation predicate", required = true)
    @NonNull
    private Predicate predicate;

}
