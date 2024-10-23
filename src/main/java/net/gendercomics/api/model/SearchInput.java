package net.gendercomics.api.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Search input consisting of searchTerm and SearchFilter(comic, creator, publisher, keyword")
public class SearchInput {
    private String searchTerm;
    private SearchFilter searchFilter;
    private String language;
}
