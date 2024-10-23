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
@ApiModel(description = "SearchFilter(comic, creator, publisher, keyword")
public class SearchFilter {
    private boolean comics;
    private boolean persons;
    private boolean publishers;
    private boolean keywords;
}
