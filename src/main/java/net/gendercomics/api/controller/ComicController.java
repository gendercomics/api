package net.gendercomics.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.ComicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@Api(tags = {"comics"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ComicController {

    private final ComicService _comicService;

    /*** public endpoints ***/

    @ApiOperation("get all comics")
    @GetMapping(path = "/comics", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> getAllComics() {
        return _comicService.findAll();
    }

    @ApiOperation("get all comics")
    @GetMapping(path = "/comicsList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> getAllComicsForList() {
        return _comicService.findAllForList();
    }

    @ApiOperation("get all comic parents (anthologies, magazines")
    @GetMapping(path = "/comics/parents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> getAllParents() {
        return _comicService.findByTypes(ComicType.anthology, ComicType.magazine);
    }

    @ApiOperation("get comics by type (ComicType.anthology, ComicType.magazine, ComicType.series)")
    @GetMapping(path = "/comics/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> getByType(@ApiParam @PathVariable("type") String type) {
        if ("series".equals(type)) {
            return _comicService.findByTypes(ComicType.comic_series, ComicType.publishing_series);
        }
        return _comicService.findByTypes(ComicType.valueOf(type));
    }

    @ApiOperation("get a comic")
    @GetMapping(path = "/comics/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comic getComic(@ApiParam @PathVariable("id") String id) {
        return _comicService.getComic(id);
    }

    @ApiOperation("get a comic in XML format")
    @GetMapping(path = "/comics/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getComicAsXml(@ApiParam @PathVariable("id") String id) throws JsonProcessingException {
        return _comicService.getComicAsXml(id);
    }

    @ApiOperation("get the number of comics in the database")
    @GetMapping(path = "/comics/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public long getComicCount() {
        return _comicService.getComicCount();
    }

    @ApiOperation("get a comic by title")
    @GetMapping(path = "/comics/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> getComicByTitle(@ApiParam @PathVariable("title") String title) {
        return _comicService.findByTitle(title);
    }

    @ApiOperation("verify if a comic title exists in the database")
    @GetMapping(path = "/comics/title/exists/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getTitleExists(@ApiParam @PathVariable("title") String title) {
        return _comicService.titleExists(title);
    }

    /*** admin endpoints - secured, only authorized access allowed ***/

    @ApiOperation("insert a comic")
    @PostMapping(path = "/comics")
    public Comic insertComic(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Comic comic) {
        return _comicService.save(comic, principal.getName());
    }

    @ApiOperation("update a comic")
    @PutMapping(path = "/comics/{id}")
    public Comic saveComic(@ApiIgnore Principal principal, @ApiParam(required = true) @RequestBody Comic comic) {
        return _comicService.save(comic, principal.getName());
    }

    @ApiOperation("delete a comic")
    @DeleteMapping(path = "/comics/{id}")
    public void deleteComic(@ApiIgnore Principal principal, @ApiParam(required = true) @PathVariable String id) {
        _comicService.delete(id);
    }

}
