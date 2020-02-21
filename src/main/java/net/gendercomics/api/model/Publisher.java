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
@Document("publishers")
@ApiModel(description = "comic book publisher")
public class Publisher implements Comparable<Publisher> {

    private String id;
    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    @ApiModelProperty(value = "URL to publisher website", required = true)
    private String name;
    @ApiModelProperty(value = "URL to publisher website")
    private String url;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Override
    public int compareTo(Publisher o) {
        return this.name.compareToIgnoreCase(o.name);
    }


}
