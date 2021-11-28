package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get("/files/");

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            log.error("Could not store the file.", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
