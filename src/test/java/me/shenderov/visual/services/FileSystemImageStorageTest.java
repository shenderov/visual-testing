package me.shenderov.visual.services;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.ResourceWrapper;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.enums.ImageType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileSystemImageStorageTest extends VisualTestingApplicationTests {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void shouldSaveImageAsMultipartFile() throws Exception {
        try {
            File file = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
            MockMultipartFile multipartFile = new MockMultipartFile("file", "base.png", "image/png", new FileInputStream(file));
            Image image = imageStorageService.save(multipartFile);
            assertTrue(image.getSize() > 0);
            assertTrue(image.getId() > 0);
            assertEquals(image.getType(), ImageType.IMAGE);
            assertEquals(image.getFilename(), "base.png");
            assertEquals(image.getChecksum(), "014df6f19e6e7e945478865265d57788");
            ResourceWrapper wrapper = imageStorageService.get(image.getId());
            assertEquals(wrapper.getResource().getFile().getName(), image.getChecksum());
            assertEquals(wrapper.getResource().getFile().length(), image.getSize());
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    void shouldSaveImageAsFile() throws IOException {
        File file = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        Image image = imageStorageService.save(file);
        assertTrue(image.getSize() > 0);
        assertTrue(image.getId() > 0);
        assertEquals(image.getType(), ImageType.IMAGE);
        assertEquals(image.getFilename(), "base.png");
        assertEquals(image.getChecksum(), "014df6f19e6e7e945478865265d57788");
        ResourceWrapper wrapper = imageStorageService.get(image.getId());
        assertEquals(wrapper.getResource().getFile().getName(), image.getChecksum());
        assertEquals(wrapper.getResource().getFile().length(), image.getSize());
    }

    @Test
    void shouldSaveTwoImageWithTheSameName() {
        File file1 = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        File file2 = new File(Objects.requireNonNull(classLoader.getResource("test/base.png")).getFile());
        Image image1 = imageStorageService.save(file1);
        Image image2 = imageStorageService.save(file2);
        assertTrue(image1.getSize() > 0);
        assertTrue(image1.getId() > 0);
        assertEquals(image1.getType(), ImageType.IMAGE);
        assertEquals(image1.getFilename(), "base.png");
        assertEquals(image1.getChecksum(), "014df6f19e6e7e945478865265d57788");
        assertTrue(image2.getSize() > 0);
        assertTrue(image2.getId() > image1.getId());
        assertEquals(image2.getType(), ImageType.IMAGE);
        assertEquals(image2.getFilename(), "base.png");
        assertEquals(image2.getChecksum(), "05d76676738c46525425cb861dc458d5");
    }

    @Test
    void shouldSaveTwoImageWithTheSameContent() {
        File file1 = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        File file2 = new File(Objects.requireNonNull(classLoader.getResource("base2.png")).getFile());
        Image image1 = imageStorageService.save(file1);
        Image image2 = imageStorageService.save(file2);
        assertTrue(image1.getSize() > 0);
        assertTrue(image1.getId() > 0);
        assertEquals(image1.getType(), ImageType.IMAGE);
        assertEquals(image1.getFilename(), "base.png");
        assertEquals(image1.getChecksum(), "014df6f19e6e7e945478865265d57788");
        assertTrue(image2.getSize() > 0);
        assertTrue(image2.getId() > image1.getId());
        assertEquals(image2.getType(), ImageType.IMAGE);
        assertEquals(image2.getFilename(), "base2.png");
        assertEquals(image2.getChecksum(), "014df6f19e6e7e945478865265d57788");
    }

    @Test
    void shouldNotSaveImageWithNullFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.save((File) null));
        String expectedMessage = "Failed to store empty file null";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldNotSaveImageWithNullMultipartFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.save((MultipartFile) null));
        String expectedMessage = "Cannot invoke \"org.springframework.web.multipart.MultipartFile.isEmpty()\" because \"file\" is null";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        System.out.println(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldNotSaveImageWithWrongPathFile() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.save(new File("")));
        String expectedMessage = "Failed to store empty file ";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldNotSaveImageWithWrongPathMultipartFile() {
        try {
            MockMultipartFile multipartFile = new MockMultipartFile("file", "base.png", "image/png", new FileInputStream(""));
            assertThrows(RuntimeException.class, () -> imageStorageService.save(multipartFile));
            fail("Should not pass with wrong path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldNotSaveImageWithEmptyFile() {
        File file = new File(Objects.requireNonNull(classLoader.getResource("empty.png")).getFile());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.save(file));
        String expectedMessage = "Failed to store empty file empty.png";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void shouldNotSaveImageWithEmptyMultipartFile() {
        File file = new File(Objects.requireNonNull(classLoader.getResource("empty.png")).getFile());
        try {
            MockMultipartFile multipartFile = new MockMultipartFile("file", "empty.png", "image/png", new FileInputStream(file));
            RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.save(multipartFile));
            String expectedMessage = "Failed to store empty file empty.png";
            String actualMessage = exception.getMessage();
            assert actualMessage != null;
            assertEquals(actualMessage, expectedMessage);
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetImageById() throws IOException {
        File file = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        Image image = imageStorageService.save(file);
        assertTrue(image.getId() > 0);
        ResourceWrapper wrapper = imageStorageService.get(image.getId());
        assertEquals(image.getFilename(), wrapper.getFilename());
        assertEquals(wrapper.getResource().getFilename(), image.getChecksum());
        assertEquals(wrapper.getResource().getFile().length(), image.getSize());
    }

    @Test
    void shouldThrowExceptionForNotExistingImage() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> imageStorageService.get(10000L));
        String expectedMessage = "Cannot invoke \"me.shenderov.visual.entities.ResourceWrapper.getResource()\" because \"resource\" is null";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertEquals(actualMessage, expectedMessage);
    }
}