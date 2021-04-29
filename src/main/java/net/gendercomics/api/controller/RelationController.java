package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.RelationService;
import net.gendercomics.api.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Api(tags = {"relations"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelationController {

    private final RelationService _relationService;

    @ApiOperation("get a map of relations for specified object id")
    @GetMapping(path = "/relations/{id}")
    public Map<String, List<Relation>> getRelationsForObject(@ApiParam(required = true) @PathVariable String id) {
        return _relationService.findAllRelationsGroupedByType(id);
    }

    @ApiOperation("get a map of relations for specified relation type and object id")
    @GetMapping(path = "/relations/{relationType}/{id}")
    public List getRelationsForObject(@ApiParam(required = true) @PathVariable String relationType, @ApiParam(required = true) @PathVariable String id) {
        return _relationService.findAllRelationsForType(relationType, id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a relation")
    @PostMapping(path = "/relations")
    public Relation insertRelation(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Relation relation) {
        return _relationService.save(relation, principal.getName());
    }

    @ApiOperation("update a relation")
    @PutMapping(path = "/relations/{id}")
    public Relation updateRelation(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Relation relation) {
        return _relationService.save(relation, principal.getName());
    }

    @ApiOperation("delete a relation")
    @PostMapping(path = "/relations/{id}")
    public void deleteRelation(@ApiParam(required = true) @PathVariable String id) {
        _relationService.delete(id);
    }

}
