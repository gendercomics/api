package net.gendercomics.api.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@ApiModel(description = "comic book title")
public class Title {

    private String id;
    @ApiModelProperty(value = "comic book title", required = true)
    private Map<Language, Text> values;

    public void addOrUpdateValue(String title, Language language) {
        if (values == null) {
            values = new HashMap<>();
        }
        Text text = values.get(language);
        if (text == null) {
            values.put(language, new Text(title, language));
        } else {
            text.setValue(title);
        }
    }
}
