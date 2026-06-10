package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "persons")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonService _personService;

    @Operation(summary = "get all persons")
    @GetMapping(path = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPersons() {
        return _personService.findAll();
    }

    @Operation(summary = "get person by id")
    @GetMapping(path = "/persons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPerson(@Parameter @PathVariable("id") String id) {
        return _personService.getPerson(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a person")
    @PostMapping(path = "/persons")
    public Person insertPerson(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Person person) {
        return _personService.insert(person, principal.getName());
    }

    @Operation(summary = "update a person")
    @PutMapping(path = "/persons/{id}")
    public Person savePerson(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Person person) {
        return _personService.save(person, principal.getName());
    }

    @Operation(summary = "delete a person")
    @DeleteMapping(path = "/persons/{id}")
    public void deletePerson(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @PathVariable String id) {
        _personService.delete(id);
    }

}
