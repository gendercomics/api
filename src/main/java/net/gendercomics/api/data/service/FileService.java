package net.gendercomics.api.data.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * Save a file to storage
     * @param file
     */
    void save(MultipartFile file);

}
