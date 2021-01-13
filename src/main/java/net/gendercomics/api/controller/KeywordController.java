package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"keywords"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeywordController {

    private final KeywordService _keywordService;

    @ApiOperation("get all keywords")
    @GetMapping(path = "/keywords", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Keyword> getKeywords(@RequestParam Optional<String> type) {
        return type.map(_keywordService::findByType).orElseGet(_keywordService::findAll);
    }

    @ApiOperation("get a keyword")
    @GetMapping(path = "/keywords/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Keyword getKeyword(@ApiParam @PathVariable("id") String id) {
        return _keywordService.getKeyword(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a keyword")
    @PostMapping(path = "/keywords")
    public Keyword insertRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Keyword keyword) {
        return _keywordService.insert(keyword, principal.getName());
    }

    @ApiOperation("update a keyword")
    @PutMapping(path = "/keywords/{id}")
    public Keyword saveRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Keyword keyword) {
        return _keywordService.save(keyword, principal.getName());
    }

    @ApiOperation("delete a keyword")
    @DeleteMapping(path = "/keywords/{id}")
    public void deletePerson(@ApiIgnore Principal principal, @ApiParam(required = true) @PathVariable String id) {
        _keywordService.delete(id);
    }

}
