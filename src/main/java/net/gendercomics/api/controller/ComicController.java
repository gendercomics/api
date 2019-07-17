package net.gendercomics.api.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.ComicService;
import net.gendercomics.api.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"comics"})
@RestController
@RequestMapping("/comics")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ComicController {

    private final ComicService _comicService;

    @ApiOperation("get all comics")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Comic> getAllComics() {
        return _comicService.findAll();
    }

    @ApiOperation("insert a comic")
    @PostMapping
    public Comic insertComic(@ApiParam(required = true) @RequestBody Comic comic) {
        return _comicService.insert(comic);
    }

    @ApiOperation("get a comic in XML format")
    @GetMapping(path = "/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getComicAsXml(@ApiParam @PathVariable("id") String id) throws JsonProcessingException {
        return _comicService.getComicAsXml(id);
    }

}
