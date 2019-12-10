package net.gendercomics.api.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"persons"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final HttpServletRequest _request;

    private final PersonService _personService;

    @ApiOperation("get person by name")
    @GetMapping(path = "/person/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Person> getPersonBySearchTerm(@ApiParam @PathVariable("name") String name) {
        return _personService.findByName(name);
    }

}
