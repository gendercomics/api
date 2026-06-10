package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.SearchService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "search")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private final SearchService _searchService;

    @Operation(summary = "search for comics in comics, creator and publishers")
    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> searchAndReturnComics(@Parameter(required = true) @RequestParam("searchTerm") String searchTerm) {
        return _searchService.searchAndReturnComics(searchTerm);
    }

    @Operation(summary = "search for comics in comics, creators, publishers and keywords")
    @PostMapping(path = "/search-web", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> searchAndReturnComics2(@Parameter(required = true) @RequestBody SearchInput searchInput) {
        return _searchService.searchAndReturnComics(searchInput);
    }

    @PostMapping(path = "/search/download", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadSearch(@Parameter(required = true) @RequestBody SearchInput searchInput) {
        String harvard = _searchService.convertResultToHarvard(_searchService.searchAndReturnComics(searchInput));
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=comics-" + searchInput.getSearchTerm() + ".txt")
                .body(harvard);
    }

}
