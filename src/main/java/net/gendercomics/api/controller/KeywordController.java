package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"keywords"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeywordController {

    private final KeywordService _keywordService;

    @ApiOperation("get all keywords")
    @GetMapping(path = "/keywords", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Keyword> getAllKeywords() {
        return _keywordService.findAll();
    }

    @ApiOperation("get a keyword")
    @GetMapping(path = "/keywords/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Keyword getKeyword(@ApiParam @PathVariable("id") String id) {
        return _keywordService.getKeyword(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a role")
    @PostMapping(path = "/keywords")
    public Keyword insertRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Keyword keyword) {
        return _keywordService.insert(keyword, principal.getName());
    }

    @ApiOperation("update a role")
    @PutMapping(path = "/keywords/{id}")
    public Keyword saveRole(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Keyword keyword) {
        return _keywordService.save(keyword, principal.getName());
    }

}
