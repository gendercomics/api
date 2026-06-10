package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "relation model")
public class Relation {

    @Schema(description = "the relation source object", required = true)
    private Object source;

    @Schema(description = "the relation predicate", required = true)
    @NonNull
    private Predicate predicate;

    @Schema(description = "the relation target object", required = true)
    private Object target;

}
