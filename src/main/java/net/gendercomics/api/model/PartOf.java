package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@Schema(description = "object containing the information ")
public class PartOf {

    @DBRef
    @Schema(description = "references comic", required = true)
    private Comic comic;

    @Schema(description = "pages in the referenced comic")
    private String pages;

}
