package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "roles")
@Schema(description = "roles involved in creating comics")
public class Role {

    private String id;
    @Indexed(name = "role_name_index", unique = true, direction = IndexDirection.ASCENDING)
    @Schema(description = "role name (artist, letterer, inker, etc.)", required = true)
    private String name;
    @Schema(description = "detailed description of the role", required = true)
    private String description;
    @Schema(description = "metadata", required = true)
    private MetaData metaData;

}

