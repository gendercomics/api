package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "object containing the search results")
public class SearchResult {

    @Getter
    private final List<Comic> comics;

    public SearchResult(List<Comic> comics) {
        this.comics = comics;
    }

    public static SearchResult emptyResult() {
        return new SearchResult(Collections.emptyList());
    }
}
