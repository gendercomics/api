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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"publishers"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublisherController {

    private final PublisherService _publisherService;

    @ApiOperation("get all persons")
    @GetMapping(path = "/publishers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Publisher> getAllPublishers() {
        return _publisherService.findAll();
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a publisher")
    @PostMapping(path = "/publishers")
    public Publisher insertComic(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Publisher publisher) {
        return _publisherService.insert(publisher, principal.getName());
    }
}
