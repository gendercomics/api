package net.gendercomics.api.integrationtest;

import net.gendercomics.api.data.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FileServiceIntegrationTest extends AbstractIntegrationTest {

    @Value("${images.path}")
    private String _root;

    @Autowired
    private FileService _fileService;

    @Test
    public void whenHasDnbCover_withIsbnForFahrradmod_ThenTrue() throws IOException {
        // Tobi Dahmen, Fahrradmod, isbn13=9783551763082
        assertThat(_fileService.hasDnbCover("9783551763082"))
                .isTrue();
    }

    @Test
    public void whenHasDnbCover_withTooShortIsbn_ThenFalse() throws IOException {
        assertThat(_fileService.hasDnbCover("97835517630"))
                .isFalse();
    }

    @Test
    public void whenHasDnbCover_withDnb404_ThenFalse() throws IOException {
        assertThat(_fileService.hasDnbCover("978-3-518-46386-5"))
                .isFalse();
    }

    @Test
    public void whenSaveDnbCover_withIsbnForFahrradmod_ThenCoverDownloaded() throws IOException {
        // Tobi Dahmen, Fahrradmod, isbn13=9783551763082
        _fileService.saveDnbCover("test", "9783551763082");

        File dir = new File(_root + "test/9783551763082-dnb-cover.jpeg");
        assertThat(dir.exists())
                .isTrue();
    }

    @AfterEach
    public void cleanup() {
        File[] files = new File(_root + "test").listFiles((dir, name) -> name.contains("-dnb-cover"));
        Arrays.asList(files).stream().forEach(File::delete);
    }
}