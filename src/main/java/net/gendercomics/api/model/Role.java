package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "roles")
@ApiModel(description = "roles involved in creating comics")
public class Role {

    private String id;
    @ApiModelProperty(value = "role name (artist, letterer, inker, etc.)", required = true)
    private String name;
    @ApiModelProperty(value = "detailed description of the role", required = true)
    private String description;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

}

