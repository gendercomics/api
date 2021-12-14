package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gendercomics.api.data.repository.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${images.path}")
    private String _root;

    private final ComicRepository _comicRepository;

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

    @Override
    public boolean hasDnbCover(String isbn) {
        String isbn13 = StringUtils.replace(isbn, "-", "");
        if (isbn13.length() != 13) {
            log.warn("isbn not an isbn13: " + isbn);
            return false;
        }
        URL url = null;
        try {
            url = new URL("https://portal.dnb.de/opac/mvb/cover?isbn=" + isbn13);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            log.debug("hasDnbCover({}): responseCode={}", isbn, huc.getResponseCode());
            return HttpURLConnection.HTTP_OK == huc.getResponseCode();
        } catch (IOException e) {
            log.error("error in hasDnbCover(" + isbn + ")", e);
        }
        return false;
    }

    @Override
    public String saveDnbCover(String comicId, String isbn) {
        checkAndCreatePath(_root + comicId);
        String fileName = null;

        try {
            URL url = new URL("https://portal.dnb.de/opac/mvb/cover?isbn=" + isbn);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            MimeType mimeType = MimeTypeUtils.parseMimeType(huc.getHeaderField("Content-Type"));
            huc.disconnect();

            if (isImageMimeType(mimeType)) {
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                fileName = isbn + "-dnb-cover." + mimeType.getSubtype();
                FileOutputStream fileOutputStream = new FileOutputStream(_root + comicId + File.separator + fileName);
                fileOutputStream.getChannel()
                        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                fileOutputStream.close();
                readableByteChannel.close();
            } else {
                log.error("url: " + url + " returned not an image");
            }
        } catch (IOException e) {
            log.error("error downloading file from DNB", e);
        }

        return fileName;
    }

    @Override
    public int downloadAllDnbCovers() {
        AtomicInteger count = new AtomicInteger();
        _comicRepository.findAll()
                .stream()
                .filter(comic -> comic.getCover() == null)
                .filter(comic -> comic.getIsbn() != null)
                .filter(comic -> hasDnbCover(comic.getIsbn()))
                .forEach(comic -> {
                    comic.setCover(saveDnbCover(comic.getId(), comic.getIsbn()));
                    _comicRepository.save(comic);
                    count.addAndGet(1);
                });
        return count.get();
    }

    private boolean isImageMimeType(MimeType mimeType) {
        return mimeType.includes(MimeTypeUtils.IMAGE_JPEG) || mimeType.includes(MimeTypeUtils.IMAGE_PNG);
    }

}
