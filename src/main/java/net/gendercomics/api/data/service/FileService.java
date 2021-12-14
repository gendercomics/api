package net.gendercomics.api.data.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void save(String comicId, MultipartFile file);

    void delete(String comicId, String fileName);

    boolean hasDnbCover(String isbn);

    String saveDnbCover(String comicId, String isbn) throws IOException;

    int downloadAllDnbCovers();
}
