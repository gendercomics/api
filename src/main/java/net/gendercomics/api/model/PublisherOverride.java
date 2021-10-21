package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "publisher override object: override location related to a comic")
public class PublisherOverride {

    @ApiModelProperty(value = "publisherId", required = true)
    private String publisherId;

    @ApiModelProperty(value = "location", required = true)
    private String location;
}
