package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Document("publishers")
@ApiModel(description = "comic book publisher")
public class Publisher implements Comparable<Publisher>, DisplayNameI18n {

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
    @Transient
    @ApiModelProperty(value = "location override related to specific comic (transient)", required = true)
    private String locationOverride;

    @Override
    public int compareTo(Publisher o) {
        return this.name.compareToIgnoreCase(o.name);
    }

    @Override
    public Map<Language, String> getDisplayNames() {
        Map<Language, String> map = new HashMap<>();
        map.put(Language.de, this.name);
        map.put(Language.en, this.name);
        return map;
    }
}
