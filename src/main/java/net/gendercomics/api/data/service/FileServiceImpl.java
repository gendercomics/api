package net.gendercomics.api.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean hasDnbCover(String isbn) throws IOException {
        String isbn13 = StringUtils.replace(isbn, "-", "");
        if (isbn13.length() != 13) {
            log.warn("isbn not an isbn13: " + isbn13);
            return false;
        }
        URL url = new URL("https://portal.dnb.de/opac/mvb/cover?isbn=" + isbn13);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("HEAD");
        return HttpURLConnection.HTTP_OK == huc.getResponseCode();
    }

    @Override
    public void saveDnbCover(String comicId, String isbn) {
        checkAndCreatePath(_root + comicId);

        try {
            URL url = new URL("https://portal.dnb.de/opac/mvb/cover?isbn=" + isbn);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            MimeType mimeType = MimeTypeUtils.parseMimeType(huc.getHeaderField("Content-Type"));
            huc.disconnect();

            if (isImageMimeType(mimeType)) {
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                String fileName = _root + comicId + File.separator + isbn + "-dnb-cover." + mimeType.getSubtype();
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
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
    }

    private boolean isImageMimeType(MimeType mimeType) {
        return mimeType.includes(MimeTypeUtils.IMAGE_JPEG) || mimeType.includes(MimeTypeUtils.IMAGE_PNG);
    }

}
