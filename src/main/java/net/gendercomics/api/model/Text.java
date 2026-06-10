package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "texts")
@Schema(description = "text model")
public class Text {

    private String id;

    @Schema(description = "metadata", required = true)
    @EqualsAndHashCode.Exclude
    private MetaData metaData;

    @Schema(description = "contains the (rich)text - formatted by tiptap editor", required = true)
    private String value;

}
