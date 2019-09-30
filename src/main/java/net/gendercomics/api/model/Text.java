package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel(description = "text object including information about the text language")
public class Text {

    private String id;
    @ApiModelProperty(value = "the text", required = true)
    private String value;
    @ApiModelProperty(value = "ISO-639-1 two letter language code", required = true)
    private Language language;

    public Text(String value, Language language) {
        this.value = value;
        this.language = language;
    }
}
