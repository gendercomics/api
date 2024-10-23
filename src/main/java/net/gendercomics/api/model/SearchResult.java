package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "object containing the search results")
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
