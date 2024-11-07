package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.impl.SearchServiceImpl;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.SearchInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"search"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private final SearchServiceImpl _searchServiceImpl;

    @ApiOperation("search for comics in comics, creator and publishers")
    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> searchAndReturnComics(@ApiParam(required = true) @RequestParam("searchTerm") String searchTerm) {
        return _searchServiceImpl.searchAndReturnComics(searchTerm);
    }

    @ApiOperation("search for comics in comics, creators, publishers and keywords")
    @PostMapping(path = "/search-web", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> searchAndReturnComics2(@ApiParam(required = true) @RequestBody SearchInput searchInput) {
        return _searchServiceImpl.searchAndReturnComics(searchInput);
    }

    @PostMapping(path = "/search/download", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> downloadSearch(@ApiParam(required = true) @RequestBody SearchInput searchInput) {
        String harvard = _searchServiceImpl.convertResultToHarvard(_searchServiceImpl.searchAndReturnComics(searchInput));
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment;filename=comics-" + searchInput.getSearchTerm() + ".txt")
                .body(harvard);
    }

}
