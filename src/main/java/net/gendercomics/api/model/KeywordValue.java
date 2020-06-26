package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@Getter
@Setter
@ApiModel(description = "keyword value per language")
@CompoundIndexes(value = {
        @CompoundIndex(name = "keyword_value_index", def = "{'name':1, 'language':1}", unique = true, sparse = true)
})
public class KeywordValue {

    @ApiModelProperty(value = "keyword name", required = true)
    private String name;

    @ApiModelProperty(value = "keyword description")
    private String description;

    @ApiModelProperty(value = "ISO-639-1 two letter language code", required = true)
    private Language language;
}
