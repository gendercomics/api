package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.model.MigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
