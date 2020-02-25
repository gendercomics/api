package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@ApiModel(description = "object containing the information ")
public class PartOf {

    @DBRef
    @ApiModelProperty(value = "references comic", required = true)
    private Comic comic;

    @ApiModelProperty(value = "pages in the referenced comic")
    private String pages;

}
