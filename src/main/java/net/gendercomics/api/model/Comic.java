package net.gendercomics.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "comics")
//@JsonTypeName("comic")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel(description = "comic book model")
public class Comic {

    //@JsonIgnore
    private String id;
    @ApiModelProperty(value = "comic book title", required = true)
    private String title;
    @ApiModelProperty(value = "comic book subtitle", required = true)
    private String subTitle;
    //@ApiModelProperty(value = "list of creators", required = true)
    //private List<Creator> creators;
    //@ApiModelProperty(value = "publisher", required = true)
    //private Publisher publisher;

}
