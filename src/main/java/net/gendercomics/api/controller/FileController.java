package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = {"files"})
@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    private final FileService _fileService;

    @PostMapping("/files/upload")
    public void upload(@ApiParam(required = true) @RequestParam("comicId") String comicId,
                       @ApiParam(required = true) @RequestParam("file") MultipartFile file) {
        _fileService.save(comicId, file);
    }

    @DeleteMapping("/files/{comicId}/{fileName}")
    public void delete(@ApiParam(required = true) @PathVariable("comicId") String comicId,
                       @ApiParam(required = true) @PathVariable("fileName") String fileName) {
        _fileService.delete(comicId, fileName);
    }

    @GetMapping("/files/dnb/cover/available/{isbn}")
    public boolean dnbHasCover(@ApiParam(required = true) @PathVariable("isbn") String isbn) {
        return _fileService.hasDnbCover(isbn);
    }

    @PostMapping("/files/dnb/cover/download")
    public void downloadDnbCover(@ApiParam(required = true) @RequestParam("comicId") String comicId,
                                 @ApiParam(required = true) @RequestParam("isbn") String isbn) {
        try {
            _fileService.saveDnbCover(comicId, isbn);
        } catch (IOException e) {
            log.error("error downloading cover image from DNB", e);
        }
    }

    @PostMapping("/files/dnb/cover/download/all")
    public void downloadAllDnbCovers() {
        _fileService.downloadAllDnbCovers();
    }

}
