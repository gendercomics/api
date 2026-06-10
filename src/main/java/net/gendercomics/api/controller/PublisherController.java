package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PublisherService;
import net.gendercomics.api.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "publishers")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController {

    private final PublisherService _publisherService;

    @Operation(summary = "get all publishers")
    @GetMapping(path = "/publishers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Publisher> getAllPublishers() {
        return _publisherService.findAll();
    }

    @Operation(summary = "get a publisher")
    @GetMapping(path = "/publishers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Publisher getPublisher(@Parameter @PathVariable("id") String id) {
        return _publisherService.getPublisher(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @Operation(summary = "insert a publisher")
    @PostMapping(path = "/publishers")
    public Publisher insertPublisher(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Publisher publisher) {
        return _publisherService.insert(publisher, principal.getName());
    }

    @Operation(summary = "update a publisher")
    @PutMapping(path = "/publishers/{id}")
    public Publisher savePublisher(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @RequestBody Publisher publisher) {
        return _publisherService.save(publisher, principal.getName());
    }

    @Operation(summary = "delete a publisher")
    @DeleteMapping(path = "/publishers/{id}")
    public void deletePerson(@Parameter(hidden = true) Principal principal, @Parameter(required = true) @PathVariable String id) {
        _publisherService.delete(id);
    }
}
