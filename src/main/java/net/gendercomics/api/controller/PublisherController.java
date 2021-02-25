package net.gendercomics.api.controller;

import java.security.Principal;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.PublisherService;
import net.gendercomics.api.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"publishers"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController {

    private final PublisherService _publisherService;

    @ApiOperation("get all publishers")
    @GetMapping(path = "/publishers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Publisher> getAllPublishers() {
        return _publisherService.findAll();
    }

    @ApiOperation("get a publisher")
    @GetMapping(path = "/publishers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Publisher getPublisher(@ApiParam @PathVariable("id") String id) {
        return _publisherService.getPublisher(id);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a publisher")
    @PostMapping(path = "/publishers")
    public Publisher insertPublisher(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Publisher publisher) {
        return _publisherService.insert(publisher, principal.getName());
    }

    @ApiOperation("update a publisher")
    @PutMapping(path = "/publishers/{id}")
    public Publisher savePublisher(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Publisher publisher) {
        return _publisherService.save(publisher, principal.getName());
    }

    @ApiOperation("delete a publisher")
    @DeleteMapping(path = "/publishers/{id}")
    public void deletePerson(@ApiIgnore Principal principal, @ApiParam(required = true) @PathVariable String id) {
        _publisherService.delete(id);
    }
}
