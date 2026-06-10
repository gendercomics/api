package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.NotFoundException;
import net.gendercomics.api.data.service.PredicateService;
import net.gendercomics.api.model.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "predicates")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PredicateController {

    private final PredicateService _predicateService;

    @Operation(summary = "get all predicates")
    @GetMapping(path = "/predicates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Predicate> getAllPredicates() {
        return _predicateService.findAll();
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a predicate")
    @PostMapping(path = "/predicates")
    public Predicate insertPredicate(@Parameter(hidden = true) Principal principal,
                                     @Parameter(required = true) @RequestParam("de") String de,
                                     @Parameter(required = true) @RequestParam("en") String en) {
        return _predicateService.save(de, en, principal.getName());
    }

    @Operation(summary = "save a predicate")
    @PutMapping(path = "/predicates/{id}")
    public Predicate savePredicate(@Parameter(hidden = true) Principal principal,
                                   @Parameter(required = true) @PathVariable("id") String id,
                                   @Parameter(required = true) @RequestParam("de") String de,
                                   @Parameter(required = true) @RequestParam("en") String en) throws NotFoundException {
        return _predicateService.save(id, de, en, principal.getName());
    }

    @Operation(summary = "delete a predicate")
    @DeleteMapping(path = "/predicates/{id}")
    public void deletePredicate(@Parameter(required = true) @PathVariable("id") String id) {
        _predicateService.delete(id);
    }
}
