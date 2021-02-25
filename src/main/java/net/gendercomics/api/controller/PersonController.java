package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = {"persons"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonService _personService;

    @ApiOperation("get all persons")
    @GetMapping(path = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons() {
        return _personService.findAll();
    }

    @ApiOperation("get person by id")
    @GetMapping(path = "/persons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPerson(@ApiParam @PathVariable("id") String id) {
        return _personService.getPerson(id);
    }

    /*
    @ApiOperation("get person by name")
    @GetMapping(path = "/persons/name/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Person> getPersonBySearchTerm(@ApiParam @PathVariable("name") String name) {
        return _personService.findByName(name);
    }
     */

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a person")
    @PostMapping(path = "/persons")
    public Person insertPerson(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Person person) {
        return _personService.insert(person, principal.getName());
    }

    @ApiOperation("update a person")
    @PutMapping(path = "/persons/{id}")
    public Person savePerson(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Person person) {
        return _personService.save(person, principal.getName());
    }

    @ApiOperation("delete a person")
    @DeleteMapping(path = "/persons/{id}")
    public void deletePerson(@ApiIgnore Principal principal, @ApiParam(required = true) @PathVariable String id) {
        _personService.delete(id);
    }

}
