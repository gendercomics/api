package net.gendercomics.api.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import net.gendercomics.api.data.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"files"})
@RestController
@CrossOrigin
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    private final FileService _fileService;

    @PostMapping("/files/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        _fileService.save(file);
    }

}
