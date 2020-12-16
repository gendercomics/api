package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Document(collection = "keywords")
@ApiModel(description = "keyword")
public class Keyword {

    private String id;

    @ApiModelProperty(value = "metadata")
    private MetaData metaData;

    @ApiModelProperty(value = "keyword type (content)", required = true)
    private KeywordType type;

    @ApiModelProperty(value = "list of keywords (one list entry per language)", required = true)
    private Map<Language, KeywordValue> values;

    @ApiModelProperty(value = "list of links")
    private List<String> links;

    @ApiModelProperty(value = "map of comments")
    private Map<String, Comment> comments;

}
