package net.gendercomics.api.data.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {
    void save(String comicId, MultipartFile file);
    void delete(String comicId, String fileName);
    boolean hasDnbCover(String isbn) throws IOException;
    void saveDnbCover(String comicId, String isbn) throws IOException;
}
