package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Tag(name = "keywords")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeywordController {

    private final KeywordService _keywordService;

    @Operation(summary = "get all keywords")
    @GetMapping(path = "/keywords", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Keyword> getKeywords(@RequestParam Optional<String> type) {
        return type.map(_keywordService::findByType).orElseGet(_keywordService::findAll);
    }

    @Operation(summary = "get a keyword")
    @GetMapping(path = "/keywords/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Keyword getKeyword(@Parameter @PathVariable("id") String id) {
        return _keywordService.getKeyword(id);
    }

    @Operation(summary = "get top level keywords")
    @GetMapping(path = "/keywords/top", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Keyword> getTopLevelKeywords() {
        return _keywordService.findTopLevelKeywords();
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a keyword")
    @PostMapping(path = "/keywords")
    public Keyword insertRole(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Keyword keyword) {
        return _keywordService.save(keyword, principal.getName());
    }

    @Operation(summary = "update a keyword")
    @PutMapping(path = "/keywords/{id}")
    public Keyword saveRole(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Keyword keyword) {
        return _keywordService.save(keyword, principal.getName());
    }

    @Operation(summary = "delete a keyword")
    @DeleteMapping(path = "/keywords/{id}")
    public void deleteKeyword(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @PathVariable String id) {
        _keywordService.delete(id);
    }

}
