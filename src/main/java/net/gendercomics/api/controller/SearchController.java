package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.impl.SearchServiceImpl;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
