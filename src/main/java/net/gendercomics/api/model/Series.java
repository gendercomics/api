package net.gendercomics.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@Schema(description = "object containing the series information")
public class Series {

    @DBRef
    @NonNull
    @Schema(description = "references comic", required = true)
    private Comic comic;

    @Schema(description = "volume in series")
    private String volume;

    @Transient
    @JsonIgnore
    public String getComicId() {
        return comic == null ? null : comic.getId();
    }
}
