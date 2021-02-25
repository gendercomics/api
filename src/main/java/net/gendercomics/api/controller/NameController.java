package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.NameService;
import net.gendercomics.api.model.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"names"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NameController {

    private final NameService _nameService;

    @ApiOperation("get all names")
    @GetMapping(path = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Name> getAllPersons() {
        return _nameService.findAll();
    }

    @ApiOperation("get all creators (searchable names")
    @GetMapping(path = "/creators", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Name> getAllCreators() {
        return _nameService.findSearchableNames();
    }

}
