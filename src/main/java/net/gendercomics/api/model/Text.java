package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "texts")
@ApiModel(description = "text model")
public class Text {

    private String id;

    @ApiModelProperty(value = "metadata", required = true)
    @EqualsAndHashCode.Exclude
    private MetaData metaData;

    @ApiModelProperty(value = "contains the (rich)text - formatted by tiptap editor", required = true)
    private String value;

}
