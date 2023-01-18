package me.shenderov.visual.repositories;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.dao.*;
import me.shenderov.visual.enums.ScreenshotStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DaoRelationsTest extends VisualTestingApplicationTests {

    @Test
    void shouldNotDeleteImageAttachedToScreenshot() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> imageRepository.delete(baseImage));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldDeleteImagesOnDeleteScreenshot() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        screenshotRepository.delete(result);
        assertFalse(screenshotRepository.existsById(result.getId()));
        assertFalse(imageRepository.existsById(baseImage.getId()));
        assertFalse(imageRepository.existsById(actualImage.getId()));
        assertFalse(imageRepository.existsById(diffImage.getId()));
    }

    //TODO VTS-1
    @Test
    @Disabled
    void shouldDeleteScreenshotsAndImagesOnDeleteBuild() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        buildRepository.delete(build);

        assertFalse(buildRepository.existsById(build.getId()));
        assertFalse(screenshotRepository.existsById(result.getId()));
        assertFalse(imageRepository.existsById(baseImage.getId()));
        assertFalse(imageRepository.existsById(actualImage.getId()));
        assertFalse(imageRepository.existsById(diffImage.getId()));
    }

    //TODO VTS-2
    @Test
    @Disabled
    void shouldDeleteBuildsScreenshotsAndImagesOnDeleteProjects() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        projectRepository.delete(project);

        assertFalse(projectRepository.existsById(build.getId()));
        assertFalse(buildRepository.existsById(build.getId()));
        assertFalse(screenshotRepository.existsById(result.getId()));
        assertFalse(imageRepository.existsById(baseImage.getId()));
        assertFalse(imageRepository.existsById(actualImage.getId()));
        assertFalse(imageRepository.existsById(diffImage.getId()));
    }
}