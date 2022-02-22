package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.impl.ComicServiceImpl;
import net.gendercomics.api.data.service.KeywordService;
import net.gendercomics.api.data.service.PersonService;
import net.gendercomics.api.data.service.PublisherService;
import net.gendercomics.api.data.service.RoleService;
import net.gendercomics.api.model.DataCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"common endpoints"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonController {

    private final BuildProperties _buildProperties;
    private final ComicServiceImpl _comicService;
    private final PersonService _personService;
    private final PublisherService _publisherService;
    private final RoleService _roleService;
    private final KeywordService _keywordService;

    @ApiOperation("retrieve API information")
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuildProperties getInfo() {
        return _buildProperties;
    }

    @ApiOperation("retrieve API information")
    @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataCount getDataCount() {
        DataCount dataCount = new DataCount();
        dataCount.setComics(_comicService.getComicCount());
        dataCount.setPersons(_personService.getPersonCount());
        dataCount.setPublishers(_publisherService.getPublisherCount());
        dataCount.setRoles(_roleService.getRoleCount());
        dataCount.setKeywords(_keywordService.getKeywordCount());
        return dataCount;
    }

}
