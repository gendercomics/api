package net.gendercomics.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Schema(description = "object containing the search results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    private List<Comic> comics;
    private List<Name> names;
    private List<Publisher> publishers;

    public static SearchResult emptyResult() {
        return new SearchResult(Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
    }
}
