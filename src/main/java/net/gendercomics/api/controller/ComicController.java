package net.gendercomics.api.controller;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"comics"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ComicController {

    private final HttpServletRequest _request;

    private final ComicService _comicService;

    /*** public endpoints ***/

    @ApiOperation("get all comics")
    @GetMapping(path = "/comics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Comic> getAllComics() {
        return _comicService.findAll();
    }

    @ApiOperation("get a comic")
    @GetMapping(path = "/comics/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Comic getComic(@ApiParam @PathVariable("id") String id) {
        return _comicService.getComic(id);
    }

    @ApiOperation("get a comic in XML format")
    @GetMapping(path = "/comics/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getComicAsXml(@ApiParam @PathVariable("id") String id) throws JsonProcessingException {
        return _comicService.getComicAsXml(id);
    }

    @ApiOperation("get the number of comics in the database")
    @GetMapping(path = "/comics/count", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public long getComicCount() throws JsonProcessingException {
        return _comicService.getComicCount();
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a comic")
    @PostMapping(path = "/admin/comics")
    public Comic insertComic(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Comic comic) {
        return _comicService.insert(comic, principal.getName());
    }

    /*** Keycloak access ***/

    private KeycloakSecurityContext getKeycloakSecurityContext() {
        return (KeycloakSecurityContext) _request.getAttribute(KeycloakSecurityContext.class.getName());
    }

}
