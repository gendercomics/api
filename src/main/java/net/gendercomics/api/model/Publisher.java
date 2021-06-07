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
public class Publisher implements Comparable<Publisher>, DisplayName {

    private String id;
    @Indexed(name = "publisher_name_index", unique = true, direction = IndexDirection.ASCENDING)
    @ApiModelProperty(value = "publisher name", required = true)
    private String name;
    @ApiModelProperty(value = "publisher location", required = true)
    private String location;
    @ApiModelProperty(value = "URL to publisher website")
    private String url;
    @ApiModelProperty(value = "metadata", required = true)
    private MetaData metaData;

    @Override
    public int compareTo(Publisher o) {
        return this.name.compareToIgnoreCase(o.name);
    }


    @Override
    public String getNameForWebAppList() {
        return this.name;
    }
}
