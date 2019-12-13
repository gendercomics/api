package net.gendercomics.api.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "comics")
//@JsonTypeName("comic")
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel(description = "comic book model")
public class Comic {

    private String id;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @ApiModelProperty(value = "comic book title", required = true)
    private String title;
    @ApiModelProperty(value = "comic book subtitle", required = false)
    private String subTitle;
    @ApiModelProperty(value = "list of creators", required = false)
    private List<Creator> creators;
    @ApiModelProperty(value = "publisher", required = false)
    private Publisher publisher;
    @ApiModelProperty(value = "location of publication", required = false)
    private String location;
    @ApiModelProperty(value = "year of publication", required = false)
    private Integer year;
    @ApiModelProperty(value = "edition", required = false)
    private String edition;
}
