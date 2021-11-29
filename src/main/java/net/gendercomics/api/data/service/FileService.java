package net.gendercomics.api.data.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void save(String comicId, MultipartFile file);

    void delete(String comicId, String fileName);
}
