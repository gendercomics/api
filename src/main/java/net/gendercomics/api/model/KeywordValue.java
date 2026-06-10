package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "keyword value per language")
@CompoundIndexes(value = {
        @CompoundIndex(name = "keyword_value_index", def = "{'name':1, 'language':1}", unique = true, sparse = true)
})
public class KeywordValue {

    @Schema(description = "keyword name", required = true)
    private String name;

    @Schema(description = "keyword description")
    private String description;

    @Schema(description = "ISO-639-1 two letter language code", required = true)
    private Language language;
}
