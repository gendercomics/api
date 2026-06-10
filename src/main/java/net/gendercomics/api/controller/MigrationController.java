package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.MigrationService;
import net.gendercomics.api.model.MigrationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "data migration endpoints")
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MigrationController {

    private final MigrationService _migrationService;

    @Operation(summary = "list comics with empty hyperlinks")
    @GetMapping(path = "/migration/list-empty-hyperlinks", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult listEmptyHyperlinks() {
        return _migrationService.listEmptyHyperlink();
    }

    @Operation(summary = "remove empty hyperlinks")
    @PostMapping(path = "/migration/remove-empty-hyperlinks", produces = MediaType.APPLICATION_JSON_VALUE)
    public MigrationResult removeEmptyHyperlinks() {
        return _migrationService.removeEmptyHyperlink();
    }

}
