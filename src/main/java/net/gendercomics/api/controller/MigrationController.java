package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.model.Comic;
import net.gendercomics.api.model.MigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"data migration endpoints"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MigrationController {

    private final MigrationService _migrationService;

    /**
     * @ApiOperation("execute comments migration from DbRef to relation")
     * @PostMapping(path = "/migration/comic-comments", produces = MediaType.APPLICATION_JSON_VALUE)
     * public MigrationResult migrateComicComments() {
     * return _migrationService.comicCommentToRelation();
     * }
     */

    @ApiOperation("execute publisher migration from publisher to publisher list")
    @PostMapping(path = "/migration/publisher", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult migratePublisher() {
        return _migrationService.publisherToPublisherList();
    }

    @ApiOperation("execute creator roles migration from roles to roles list")
    @PostMapping(path = "/migration/creator-roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult migrateCreatorRoles() {
        return _migrationService.roleToRoleList();
    }

    @ApiOperation("execute hyperlink migration from hyperlink to hyperlink list")
    @PostMapping(path = "/migration/hyperlink", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult migrateHyperLinks() {
        return _migrationService.linkToLinkList();
    }

    @ApiOperation("execute hyperlink migration from hyperlink to hyperlink list")
    @PostMapping(path = "/migration/remove-hyperlink", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult removeHyperLinks() {
        return _migrationService.removeHyperLink();
    }

    @ApiOperation("list all comics with existing series association")
    @GetMapping(path = "/migration/list-series", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comic> listSeries() {
        return _migrationService.listComicsWithSeries();
    }

    @ApiOperation("execute series migration from series to series list")
    @PostMapping(path = "/migration/series", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult migrateSeries() {
        return _migrationService.seriesToSeriesList();
    }

    @ApiOperation("remove all series single entries (set null")
    @PostMapping(path = "/migration/remove-series", produces = MediaType.APPLICATION_JSON_VALUE)
    public int removeSeries() {
        return _migrationService.removeSeries();
    }

}
