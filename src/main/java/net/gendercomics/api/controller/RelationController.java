package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.RelationService;
import net.gendercomics.api.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"relations"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelationController {

    private final RelationService _relationService;

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a relation")
    @PostMapping(path = "/relations")
    public Relation insertRelation(@ApiIgnore Principal principal, @ApiParam(required = true) @Valid @RequestBody Relation relation) {
        return _relationService.save(relation, principal.getName());
    }

    @ApiOperation("update a relation")
    @PutMapping(path = "/relations/{id}")
    public Relation updateRelation(@ApiIgnore Principal principal, @ApiParam(required = true) @Valid @RequestBody Relation relation) {
        return _relationService.save(relation, principal.getName());
    }

    @ApiOperation("delete a relation")
    @DeleteMapping(path = "/relations/{id}")
    public void deleteRelation(@ApiParam(required = true) @PathVariable String id) {
        _relationService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
