package net.gendercomics.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "files")
@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    private final FileService _fileService;

    @PostMapping("/files/upload")
    public void upload(@Parameter(required = true) @RequestParam("comicId") String comicId,
                       @Parameter(required = true) @RequestParam("file") MultipartFile file) {
        _fileService.save(comicId, file);
    }

    @DeleteMapping("/files/{comicId}/{fileName}")
    public void delete(@Parameter(required = true) @PathVariable("comicId") String comicId,
                       @Parameter(required = true) @PathVariable("fileName") String fileName) {
        _fileService.delete(comicId, fileName);
    }

    @GetMapping("/files/dnb/cover/available/{isbn}")
    public boolean dnbHasCover(@Parameter(required = true) @PathVariable("isbn") String isbn) {
        return _fileService.hasDnbCover(isbn);
    }

    @PostMapping("/files/dnb/cover/download")
    public String downloadDnbCover(@Parameter(required = true) @RequestParam("comicId") String comicId,
                                   @Parameter(required = true) @RequestParam("isbn") String isbn) {
        try {
            return _fileService.saveDnbCover(comicId, isbn);
        } catch (IOException e) {
            log.error("error downloading cover image from DNB", e);
            return null;
        }
    }

    @PostMapping("/files/dnb/cover/download/all")
    public int downloadAllDnbCovers() {
        return _fileService.downloadAllDnbCovers();
    }

}
