package me.shenderov.visual.repositories;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.entities.dao.Screenshot;
import me.shenderov.visual.enums.ScreenshotStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScreenshotRepositoryTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateNewScreenshot() {
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
        assertEquals(screenshot.getName(), result.getName());
        assertEquals(screenshot.getReference(), result.getReference());
        assertEquals(screenshot.getProject(), result.getProject());
        assertEquals(screenshot.getBuild(), result.getBuild());
        assertEquals(screenshot.getBaseImage(), result.getBaseImage());
        assertEquals(screenshot.getActualImage(), result.getActualImage());
        assertEquals(screenshot.getDiffImage(), result.getDiffImage());
        assertEquals(screenshot.getDiffRate(), result.getDiffRate());
        assertEquals(screenshot.getStatus(), result.getStatus());
    }

    @Test
    void shouldNotCreateScreenshotWithoutName() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullName() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName(null);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithEmptyName() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName("");
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithTooLongName() {
        String name = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName(name);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        assertEquals(screenshot.getName(), result.getName());

        screenshot.setName(name + "1");
        screenshot.setReference("Screenshot reference 1 " + uuid);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateScreenshotsWithTheSameNameInDifferentProjects() {
        String uuid = UUID.randomUUID().toString();
        Project project1 = createProject();
        Project project2 = createProject();
        Build build1 = createBuild(project1);
        Build build2 = createBuild(project1);
        Build build3 = createBuild(project2);

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference 1 " + uuid);
        screenshot.setProject(project1);
        screenshot.setBuild(build1);
        screenshot.setBaseImage(createImage());
        screenshot.setActualImage(createImage());
        screenshot.setDiffImage(createImage());
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        assertEquals(screenshot.getName(), result.getName());

        Screenshot anotherScreenshot = new Screenshot();
        anotherScreenshot.setName("Screenshot name " + uuid);
        anotherScreenshot.setReference("Screenshot reference 2 " + uuid);
        anotherScreenshot.setProject(project1);
        anotherScreenshot.setBuild(build1);
        anotherScreenshot.setBaseImage(createImage());
        anotherScreenshot.setActualImage(createImage());
        anotherScreenshot.setDiffImage(createImage());
        anotherScreenshot.setDiffRate(0.0F);
        anotherScreenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(anotherScreenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));

        Screenshot thirdScreenshot = new Screenshot();
        thirdScreenshot.setName("Screenshot name " + uuid);
        thirdScreenshot.setReference("Screenshot reference 3 " + uuid);
        thirdScreenshot.setProject(project1);
        thirdScreenshot.setBuild(build2);
        thirdScreenshot.setBaseImage(createImage());
        thirdScreenshot.setActualImage(createImage());
        thirdScreenshot.setDiffImage(createImage());
        thirdScreenshot.setDiffRate(0.0F);
        thirdScreenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result1 = screenshotRepository.save(thirdScreenshot);
        assertTrue(result1.getId() > result.getId());
        assertEquals(thirdScreenshot.getName(), result1.getName());

        Screenshot fourthScreenshot = new Screenshot();
        fourthScreenshot.setName("Screenshot name " + uuid);
        fourthScreenshot.setReference("Screenshot reference 4 " + uuid);
        fourthScreenshot.setProject(project2);
        fourthScreenshot.setBuild(build3);
        fourthScreenshot.setBaseImage(createImage());
        fourthScreenshot.setActualImage(createImage());
        fourthScreenshot.setDiffImage(createImage());
        fourthScreenshot.setDiffRate(0.0F);
        fourthScreenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result2 = screenshotRepository.save(fourthScreenshot);
        assertTrue(result2.getId() > result1.getId());
        assertEquals(fourthScreenshot.getName(), result2.getName());
    }

    @Test
    void shouldNotCreateScreenshotWithoutReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference(null);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithEmptyReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("");
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithTooLongReference() {
        String ref = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setName(ref);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        assertEquals(screenshot.getName(), result.getName());

        screenshot.setReference(ref + "1");
        screenshot.setName("Screenshot name 1 " + uuid);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateScreenshotsWithTheSameReferenceInDifferentProjects() {
        String uuid = UUID.randomUUID().toString();
        Project project1 = createProject();
        Project project2 = createProject();
        Build build1 = createBuild(project1);
        Build build2 = createBuild(project1);
        Build build3 = createBuild(project2);

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project1);
        screenshot.setBuild(build1);
        screenshot.setBaseImage(createImage());
        screenshot.setActualImage(createImage());
        screenshot.setDiffImage(createImage());
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        assertEquals(screenshot.getName(), result.getName());

        Screenshot anotherScreenshot = new Screenshot();
        anotherScreenshot.setName("Screenshot name 2 " + uuid);
        anotherScreenshot.setReference("Screenshot reference" + uuid);
        anotherScreenshot.setProject(project1);
        anotherScreenshot.setBuild(build1);
        anotherScreenshot.setBaseImage(createImage());
        anotherScreenshot.setActualImage(createImage());
        anotherScreenshot.setDiffImage(createImage());
        anotherScreenshot.setDiffRate(0.0F);
        anotherScreenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(anotherScreenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));

        Screenshot thirdScreenshot = new Screenshot();
        thirdScreenshot.setName("Screenshot name 3 " + uuid);
        thirdScreenshot.setReference("Screenshot reference" + uuid);
        thirdScreenshot.setProject(project1);
        thirdScreenshot.setBuild(build2);
        thirdScreenshot.setBaseImage(createImage());
        thirdScreenshot.setActualImage(createImage());
        thirdScreenshot.setDiffImage(createImage());
        thirdScreenshot.setDiffRate(0.0F);
        thirdScreenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result1 = screenshotRepository.save(thirdScreenshot);
        assertTrue(result1.getId() > result.getId());
        assertEquals(thirdScreenshot.getName(), result1.getName());

        Screenshot fourthScreenshot = new Screenshot();
        fourthScreenshot.setName("Screenshot name 4 " + uuid);
        fourthScreenshot.setReference("Screenshot reference" + uuid);
        fourthScreenshot.setProject(project2);
        fourthScreenshot.setBuild(build3);
        fourthScreenshot.setBaseImage(createImage());
        fourthScreenshot.setActualImage(createImage());
        fourthScreenshot.setDiffImage(createImage());
        fourthScreenshot.setDiffRate(0.0F);
        fourthScreenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result2 = screenshotRepository.save(fourthScreenshot);
        assertTrue(result2.getId() > result1.getId());
        assertEquals(fourthScreenshot.getName(), result2.getName());
    }

    @Test
    void shouldNotCreateScreenshotWithoutProject() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullProject() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(null);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNotExistingProject() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(new Project());
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "transient instance must be saved before current operation";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithoutBuild() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullBuild() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(null);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNotExistingBuild() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(new Build());
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "transient instance must be saved before current operation";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithoutBaseImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullBaseImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(null);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNotExistingBaseImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(new Image());
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "transient instance must be saved before current operation";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithoutActualImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullActualImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(null);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNotExistingActualImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(new Image());
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "transient instance must be saved before current operation";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithoutDiffImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullDiffImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(null);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNotExistingDiffImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(new Image());
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "transient instance must be saved before current operation";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateNewScreenshotUsingTheSameImage() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image image = createImage();

        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name " + uuid);
        screenshot.setReference("Screenshot reference " + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(image);
        screenshot.setActualImage(image);
        screenshot.setDiffImage(image);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(ScreenshotStatus.NEW);

        Screenshot result = screenshotRepository.save(screenshot);
        assertTrue(result.getId() > 0);
        assertEquals(screenshot.getName(), result.getName());
        assertEquals(screenshot.getReference(), result.getReference());
        assertEquals(screenshot.getProject(), result.getProject());
        assertEquals(screenshot.getBuild(), result.getBuild());
        assertEquals(screenshot.getBaseImage(), result.getBaseImage());
        assertEquals(screenshot.getActualImage(), result.getActualImage());
        assertEquals(screenshot.getDiffImage(), result.getDiffImage());
        assertEquals(screenshot.getDiffRate(), result.getDiffRate());
        assertEquals(screenshot.getStatus(), result.getStatus());
    }

    @Test
    void shouldNotCreateScreenshotWithoutDiffRate() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullDiffRate() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(null);
        screenshot.setStatus(ScreenshotStatus.NEW);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithoutStatus() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateScreenshotWithNullStatus() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build = createBuild(project);
        Image baseImage = createImage();
        Image actualImage = createImage();
        Image diffImage = createImage();
        Screenshot screenshot = new Screenshot();
        screenshot.setName("Screenshot name 1 " + uuid);
        screenshot.setReference("Screenshot reference" + uuid);
        screenshot.setProject(project);
        screenshot.setBuild(build);
        screenshot.setBaseImage(baseImage);
        screenshot.setActualImage(actualImage);
        screenshot.setDiffImage(diffImage);
        screenshot.setDiffRate(0.0F);
        screenshot.setStatus(null);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> screenshotRepository.save(screenshot));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldFindLatestScreenshotInProjectByName() {
        String uuid = UUID.randomUUID().toString();
        Project project = createProject();
        Build build1 = createBuild(project);
        Build build2 = createBuild(project);
        String name = "Screenshot name" + uuid;

        Screenshot screenshot1 = new Screenshot();
        screenshot1.setName(name);
        screenshot1.setReference("Screenshot reference" + uuid);
        screenshot1.setProject(project);
        screenshot1.setBuild(build1);
        screenshot1.setBaseImage(createImage());
        screenshot1.setActualImage(createImage());
        screenshot1.setDiffImage(createImage());
        screenshot1.setDiffRate(0.0F);
        screenshot1.setStatus(ScreenshotStatus.NEW);

        Screenshot screenshot2 = new Screenshot();
        screenshot2.setName("Screenshot name 2 " + uuid);
        screenshot2.setReference("Screenshot reference 2" + uuid);
        screenshot2.setProject(project);
        screenshot2.setBuild(build1);
        screenshot2.setBaseImage(createImage());
        screenshot2.setActualImage(createImage());
        screenshot2.setDiffImage(createImage());
        screenshot2.setDiffRate(0.0F);
        screenshot2.setStatus(ScreenshotStatus.NEW);

        Screenshot screenshot3 = new Screenshot();
        screenshot3.setName(name);
        screenshot3.setReference("Screenshot reference 2" + uuid);
        screenshot3.setProject(project);
        screenshot3.setBuild(build2);
        screenshot3.setBaseImage(createImage());
        screenshot3.setActualImage(createImage());
        screenshot3.setDiffImage(createImage());
        screenshot3.setDiffRate(0.0F);
        screenshot3.setStatus(ScreenshotStatus.NEW);

        Screenshot result1 = screenshotRepository.save(screenshot1);
        Screenshot result2 = screenshotRepository.save(screenshot2);
        Screenshot result3 = screenshotRepository.save(screenshot3);

        List<Screenshot> expectedBuildList = new ArrayList<>();
        expectedBuildList.add(result1);
        expectedBuildList.add(result2);

        Iterable<Screenshot> builds = screenshotRepository.findAllByBuild(build1);
        List<Screenshot> actualBuildList = new ArrayList<>();
        builds.forEach(actualBuildList::add);
        assertEquals(expectedBuildList.size(), actualBuildList.size());
        assertTrue(expectedBuildList.containsAll(actualBuildList));

        Optional<Screenshot> screenshotOptional = screenshotRepository.findTopByProjectAndNameOrderByBuildDesc(project, name);
        assertTrue(screenshotOptional.isPresent());
        assertEquals(result3, screenshotOptional.get());
    }
}