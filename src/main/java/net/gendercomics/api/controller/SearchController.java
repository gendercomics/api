package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.elastic.service.SearchService;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"search"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private final SearchService _searchService;

    @ApiOperation("search a comic by title")
    @PostMapping(path = "/search/comics/title")
    public List<Comic> findComicsByTitle(@ApiParam(required = true) @RequestBody String searchTerm) {
        return _searchService.search(searchTerm);
    }

}
