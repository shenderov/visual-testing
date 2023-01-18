package me.shenderov.visual.services;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.ComparisonResult;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.enums.ImageType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageComparisonServiceTest extends VisualTestingApplicationTests {

    private final ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void shouldCompareEqualImages() throws IOException {
        File baseFile = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        Image base = imageStorageService.save(baseFile);
        ComparisonResult result = imageComparisonService.doCompare(base, base);
        assertTrue(result.getDiffImage().getSize() > 0);
        assertEquals(result.getDiffRate(), 0);
        assertFalse(result.getSizeMismatch());
        assertEquals(result.getDiffImage().getChecksum(), base.getChecksum());
        assertEquals(result.getDiffImage().getType(), ImageType.IMAGE);
    }

    @Test
    void shouldCompareDiffImages() throws IOException {
        File baseFile = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        File actualFile = new File(Objects.requireNonNull(classLoader.getResource("actual.png")).getFile());
        Image base = imageStorageService.save(baseFile);
        Image actual = imageStorageService.save(actualFile);
        ComparisonResult result = imageComparisonService.doCompare(base, actual);
        assertTrue(result.getDiffImage().getSize() > 0);
        assertEquals(result.getDiffRate(), 0.35034126F);
        assertFalse(result.getSizeMismatch());
        assertEquals(result.getDiffImage().getChecksum(), "376c7a4b6a83801d44019a426996b135");
        assertEquals(result.getDiffImage().getType(), ImageType.IMAGE);
    }

    @Test
    void shouldNotCompareSizeMismatchImages() throws IOException {
        File baseFile = new File(Objects.requireNonNull(classLoader.getResource("base.png")).getFile());
        File smallFile = new File(Objects.requireNonNull(classLoader.getResource("small.png")).getFile());
        Image base = imageStorageService.save(baseFile);
        Image small = imageStorageService.save(smallFile);
        ComparisonResult result = imageComparisonService.doCompare(base, small);
        assertNull(result.getDiffImage());
        assertEquals(result.getDiffRate(), 0);
        assertTrue(result.getSizeMismatch());
    }
}