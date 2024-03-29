package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@ApiModel(description = "object containing the series information")
public class Series {

    @DBRef
    @NonNull
    @ApiModelProperty(value = "references comic", required = true)
    private Comic comic;

    @ApiModelProperty(value = "volume in series")
    private String volume;

    @Transient
    @JsonIgnore
    public String getComicId() {
        return comic == null ? null : comic.getId();
    }
}
