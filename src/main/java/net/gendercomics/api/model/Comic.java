package net.gendercomics.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Setter
@Document(collection = "comics")
//@JsonTypeName("comic")
//@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonInclude(Include.NON_NULL)
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
    @DBRef
    private Publisher publisher;
    @ApiModelProperty(value = "location of publication", required = false)
    private String location;
    @ApiModelProperty(value = "year of publication", required = false)
    private Integer year;
    @ApiModelProperty(value = "edition", required = false)
    private String edition;
    @ApiModelProperty(value = "link", required = false)
    private String link;
    @ApiModelProperty(value = "isbn", required = false)
    private String isbn;
}
