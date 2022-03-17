package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PredicateService;
import net.gendercomics.api.model.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Api(tags = {"predicates"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PredicateController {

    private final PredicateService _predicateService;

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a predicate")
    @PostMapping(path = "/predicates")
    public Predicate insertPredicate(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Predicate predicate) {
        return _predicateService.save(predicate);
    }
}
