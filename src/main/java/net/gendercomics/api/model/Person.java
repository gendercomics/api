package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Document("persons")
@ApiModel(description = "persons involved in the process of creating comics")
public class Person {

    private String id;
    @ApiModelProperty(value = "first name", required = true)
    private String firstName;
    @ApiModelProperty(value = "last name", required = true)
    private String lastName;
    @ApiModelProperty(value = "wikidata", required = true)
    private String wikiData;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Override
    public String toString() {
        return StringUtils.trimWhitespace(firstName + " " + lastName);
    }

}
