package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode()
public class RelationIds {

    @Schema(description = "the relation target object id", required = true)
    private String sourceId;

    @Schema(description = "the relation predicate object id", required = true)
    private String predicateId;

    @Schema(description = "the relation target object id", required = true)
    private String targetId;

}
