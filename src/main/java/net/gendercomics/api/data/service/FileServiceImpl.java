package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${images.path}")
    private String _root;

    @Override
    public void save(String comicId, MultipartFile file) {
        String path = _root + comicId;
        checkAndCreatePath(path);

        try {
            Files.copy(file.getInputStream(), Paths.get(path).resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            log.error("Could not store the file.", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(String comicId, String fileName) {
        log.debug("delete: " + comicId + "/" + fileName);
        try {
            Files.delete(Paths.get(_root + comicId + File.separator + fileName));
        } catch (IOException e) {
            log.error("Could not delete the file.", e);
            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
        }
    }

    private void checkAndCreatePath(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

}
