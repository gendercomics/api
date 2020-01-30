package net.gendercomics.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {

    private String id;
    @ApiModelProperty(value = "role name (artist, letterer, inker, etc.)", required = true)
    private String name;
    @ApiModelProperty(value = "detailed description of the role", required = true)
    private String description;

}

