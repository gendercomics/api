package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PredicateService;
import net.gendercomics.api.model.Predicate;
import net.gendercomics.api.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = {"predicates"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PredicateController {

    private final PredicateService _predicateService;

    @ApiOperation("get all predicates")
    @GetMapping(path = "/predicates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Predicate> getAllPredicates() {
        return _predicateService.findAll();
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a predicate")
    @PostMapping(path = "/predicates")
    public Predicate insertPredicate(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Predicate predicate) {
        return _predicateService.save(predicate);
    }
}
