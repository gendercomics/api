package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.model.MigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("list comics with empty hyperlinks")
    @GetMapping(path = "/migration/list-empty-hyperlinks", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult listEmptyHyperlinks() {
        return _migrationService.listEmptyHyperlink();
    }

    @ApiOperation("remove empty hyperlinks")
    @PostMapping(path = "/migration/remove-empty-hyperlinks", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult removeEmptyHyperlinks() {
        return _migrationService.removeEmptyHyperlink();
    }

}
