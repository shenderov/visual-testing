package me.shenderov.visual.handlers;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.BuildInfo;
import me.shenderov.visual.entities.CreateBuildWrapper;
import me.shenderov.visual.entities.dao.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuildRequestHandlerImplTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateNewBuild() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build" + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(createProject().getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());
    }

    @Test
    void shouldNotCreateNewBuildWithoutName() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build" + uuid);
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullName() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build" + uuid);
        wrapper.setName(null);
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithEmptyName() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build" + uuid);
        wrapper.setName("");
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithTooLongName() {
        String name = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build" + uuid);
        wrapper.setName(name);
        wrapper.setProjectId(createProject().getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapperInvalid = new CreateBuildWrapper();
        wrapperInvalid.setReference("Build1" + uuid);
        wrapperInvalid.setName(name + "1");
        wrapperInvalid.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapperInvalid));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateTwoBuildsWithTheSameNameInOneProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref" + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(project.getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper2 = new CreateBuildWrapper();
        wrapper2.setReference("Build ref" + uuid);
        wrapper2.setName("Build name " + uuid);
        wrapper2.setProjectId(project.getId());
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRequestHandler.createBuild(wrapper2));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateTwoBuildsWithTheSameNameInDifferentProjects() {
        Project project1 = createProject();
        Project project2 = createProject();
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref" + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(project1.getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper1 = new CreateBuildWrapper();
        wrapper1.setReference("Build ref2" + uuid);
        wrapper1.setName("Build name " + uuid);
        wrapper1.setProjectId(project2.getId());
        BuildInfo buildInfo1 = buildRequestHandler.createBuild(wrapper1);
        assertTrue(buildInfo1.getId() > 0);
        assertEquals(wrapper1.getName(), buildInfo1.getName());
        assertEquals(wrapper1.getReference(), buildInfo1.getReference());
        assertEquals(wrapper1.getProjectId(), buildInfo1.getProjectId());
    }

    @Test
    void shouldNotCreateNewBuildWithoutReference() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullReference() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setName("Build name " + uuid);
        wrapper.setReference(null);
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithEmptyReference() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setName("Build name " + uuid);
        wrapper.setReference("");
        wrapper.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithTooLongReference() {
        String ref = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference(ref);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(createProject().getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper1 = new CreateBuildWrapper();
        wrapper1.setReference(ref + 1);
        wrapper1.setName("Build name " + uuid);
        wrapper1.setProjectId(createProject().getId());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRequestHandler.createBuild(wrapper1));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateTwoBuildsWithTheSameReferenceInOneProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(project.getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper1 = new CreateBuildWrapper();
        wrapper1.setReference("Build ref " + uuid);
        wrapper1.setName("Build name " + uuid);
        wrapper1.setProjectId(project.getId());
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRequestHandler.createBuild(wrapper1));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateTwoBuildsWithTheSameReferenceInDifferentProjects() {
        Project project1 = createProject();
        Project project2 = createProject();
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(project1.getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper1 = new CreateBuildWrapper();
        wrapper1.setReference("Build ref " + uuid);
        wrapper1.setName("Build name " + uuid);
        wrapper1.setProjectId(project2.getId());
        BuildInfo buildInfo1 = buildRequestHandler.createBuild(wrapper1);
        assertTrue(buildInfo1.getId() > 0);
        assertEquals(wrapper1.getName(), buildInfo1.getName());
        assertEquals(wrapper1.getReference(), buildInfo1.getReference());
        assertEquals(wrapper1.getProjectId(), buildInfo1.getProjectId());
    }

    @Test
    void shouldNotCreateNewBuildWithoutProject() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "The given id must not be null!";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage), String.format("%s is not equal to %s", actualMessage, expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullProjectId() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "The given id must not be null!";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage), String.format("%s is not equal to %s", actualMessage, expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNotExistingProjectId() {
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(10000L);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> buildRequestHandler.createBuild(wrapper));
        String expectedMessage = "Project is not found";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage), String.format("%s is not equal to %s", actualMessage, expectedMessage));
    }

    @Test
    void shouldFindListOfBuildsByProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        CreateBuildWrapper wrapper = new CreateBuildWrapper();
        wrapper.setReference("Build ref " + uuid);
        wrapper.setName("Build name " + uuid);
        wrapper.setProjectId(project.getId());
        BuildInfo buildInfo = buildRequestHandler.createBuild(wrapper);
        assertTrue(buildInfo.getId() > 0);
        assertEquals(wrapper.getName(), buildInfo.getName());
        assertEquals(wrapper.getReference(), buildInfo.getReference());
        assertEquals(wrapper.getProjectId(), buildInfo.getProjectId());

        CreateBuildWrapper wrapper1 = new CreateBuildWrapper();
        wrapper1.setReference("Build ref 1 " + uuid);
        wrapper1.setName("Build name 1 " + uuid);
        wrapper1.setProjectId(project.getId());
        BuildInfo buildInfo1 = buildRequestHandler.createBuild(wrapper1);
        assertTrue(buildInfo1.getId() > 0);
        assertEquals(wrapper1.getName(), buildInfo1.getName());
        assertEquals(wrapper1.getReference(), buildInfo1.getReference());
        assertEquals(wrapper1.getProjectId(), buildInfo1.getProjectId());

        List<BuildInfo> expectedBuildList = new ArrayList<>();
        expectedBuildList.add(buildInfo);
        expectedBuildList.add(buildInfo1);

        Iterable<BuildInfo> builds = buildRequestHandler.getBuildList(project.getId());
        List<BuildInfo> actualBuildList = new ArrayList<>();
        builds.forEach(actualBuildList::add);
        assertEquals(expectedBuildList.size(), actualBuildList.size());
        assertTrue(expectedBuildList.containsAll(actualBuildList));
    }
}