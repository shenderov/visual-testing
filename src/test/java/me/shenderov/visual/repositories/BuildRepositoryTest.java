package me.shenderov.visual.repositories;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.dao.Build;
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
class BuildRepositoryTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateNewBuild() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setName("Project name " + uuid);
        build.setProject(createProject());
        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());
    }

    @Test
    void shouldNotCreateNewBuildWithoutName() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(createProject());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullName() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(createProject());
        build.setName(null);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithEmptyName() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(createProject());
        build.setName("");
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithTooLongName() {
        String name = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setName(name);
        build.setProject(createProject());
        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        anotherBuild.setReference("Project 1" + uuid);
        anotherBuild.setName(name + "1");
        anotherBuild.setProject(createProject());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(anotherBuild));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateTwoBuildsWithTheSameNameInOneProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(project);
        build.setName("Project name " + uuid);

        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        anotherBuild.setReference("Project 1" + uuid);
        anotherBuild.setProject(project);
        anotherBuild.setName("Project name " + uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRepository.save(anotherBuild));
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
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(project1);
        build.setName("Project name " + uuid);

        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        anotherBuild.setReference("Project 1" + uuid);
        anotherBuild.setProject(project2);
        anotherBuild.setName("Project name " + uuid);
        Build result1 = buildRepository.save(anotherBuild);
        assertTrue(result1.getId() > 0);
        assertEquals(anotherBuild.getName(), result1.getName());
        assertEquals(anotherBuild.getReference(), result1.getReference());
        assertEquals(anotherBuild.getProject(), result1.getProject());
    }

    @Test
    void shouldNotCreateNewBuildWithoutReference() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setName("Project name " + uuid);
        build.setProject(createProject());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullReference() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference(null);
        build.setProject(createProject());
        build.setName("Project name " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithEmptyReference() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("");
        build.setProject(createProject());
        build.setName("Project name " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(build));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithTooLongReference() {
        String ref = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference(ref);
        build.setName("Project name " + uuid);
        build.setProject(createProject());
        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        build.setReference(ref + "1");
        build.setName("Project name 1 " + uuid);
        anotherBuild.setProject(createProject());
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> buildRepository.save(anotherBuild));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateTwoBuildsWithTheSameReferenceInOneProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(project);
        build.setName("Project name " + uuid);

        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        anotherBuild.setReference("Project" + uuid);
        anotherBuild.setProject(project);
        anotherBuild.setName("Project name 1 " + uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRepository.save(anotherBuild));
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
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setProject(project1);
        build.setName("Project name " + uuid);

        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build anotherBuild = new Build();
        anotherBuild.setReference("Project" + uuid);
        anotherBuild.setProject(project2);
        anotherBuild.setName("Project name 1 " + uuid);
        Build result1 = buildRepository.save(anotherBuild);
        assertTrue(result1.getId() > 0);
        assertEquals(anotherBuild.getName(), result1.getName());
        assertEquals(anotherBuild.getReference(), result1.getReference());
        assertEquals(anotherBuild.getProject(), result1.getProject());
    }

    @Test
    void shouldNotCreateNewBuildWithoutProject() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setName("Project name " + uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRepository.save(build));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNullProject() {
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setName("Project name " + uuid);
        build.setProject(null);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRepository.save(build));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewBuildWithNotExistingProject() {
        Project project = new Project();
        project.setId(10000L);
        project.setName("Project name");
        project.setReference("Project Reference");
        project.setDescription("Project Description");

        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Project" + uuid);
        build.setName("Project name " + uuid);
        build.setProject(project);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> buildRepository.save(build));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldFindListOfBuildsByProject() {
        Project project = createProject();
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Build ref " + uuid);
        build.setName("Build name " + uuid);
        build.setProject(project);
        Build result = buildRepository.save(build);
        assertTrue(result.getId() > 0);
        assertEquals(build.getName(), result.getName());
        assertEquals(build.getReference(), result.getReference());
        assertEquals(build.getProject(), result.getProject());

        Build build1 = new Build();
        build1.setReference("Build ref 1 " + uuid);
        build1.setName("Build name 1 " + uuid);
        build1.setProject(project);
        Build result1 = buildRepository.save(build1);
        assertTrue(result1.getId() > 0);
        assertEquals(build1.getName(), result1.getName());
        assertEquals(build1.getReference(), result1.getReference());
        assertEquals(build1.getProject(), result1.getProject());

        List<Build> expectedBuildList = new ArrayList<>();
        expectedBuildList.add(result);
        expectedBuildList.add(result1);

        Iterable<Build> builds = buildRepository.findAllByProject(project);
        List<Build> actualBuildList = new ArrayList<>();
        builds.forEach(actualBuildList::add);
        System.out.println(actualBuildList);
        System.out.println(expectedBuildList);
        assertEquals(expectedBuildList.size(), actualBuildList.size());
        assertTrue(expectedBuildList.containsAll(actualBuildList));
    }
}