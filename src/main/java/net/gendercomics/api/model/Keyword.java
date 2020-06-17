package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "keywords")
@ApiModel(description = "keyword")
public class Keyword implements Comparable<Keyword> {

    private String id;

    @ApiModelProperty(value = "metadata")
    private MetaData metaData;

    @ApiModelProperty(value = "keyword name", required = true)
    @Indexed(name = "keyword_name_index", direction = IndexDirection.ASCENDING, unique = true)
    private String name;

    @ApiModelProperty(value = "keyword type (content)", required = true)
    private KeywordType type;

    @ApiModelProperty(value = "description")
    private String description;

    @Override
    public int compareTo(Keyword o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
