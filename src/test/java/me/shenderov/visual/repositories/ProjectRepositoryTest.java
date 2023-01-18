package me.shenderov.visual.repositories;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.dao.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRepositoryTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateNewProject() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("Project Description " + uuid);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals(project.getDescription(), result.getDescription());
    }

    @Test
    void shouldNotCreateNewProjectWithoutName() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithNullName() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setName(null);
        project.setReference("Project" + uuid);
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithEmptyName() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setName("");
        project.setReference("Project" + uuid);
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithTooLongName() {
        String uuid = UUID.randomUUID().toString();
        String name = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String ref = "Project ref" + uuid;
        String description = "Project Description " + uuid;
        Project project = new Project();
        project.setName(name);
        project.setReference(ref);
        project.setDescription("Project Description " + uuid);
        project = projectRepository.save(project);
        assertTrue(project.getId() > 0);
        assertEquals(name, project.getName());
        assertEquals(ref, project.getReference());
        assertEquals(description, project.getDescription());

        project.setName(name + "1");
        project.setReference(ref + "1");
        Project finalProject = project;
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(finalProject));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithDuplicateName() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project ref" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("Project Description " + uuid);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals(project.getDescription(), result.getDescription());

        Project anotherProject = new Project();
        anotherProject.setReference("Project ref1" + uuid);
        anotherProject.setName("Project name " + uuid);
        anotherProject.setDescription("Project Description " + uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> projectRepository.save(anotherProject));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithoutReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setName("Project" + uuid);
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithNullReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setName("Project" + uuid);
        project.setReference(null);
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithEmptyReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setName("Project" + uuid);
        project.setReference("");
        project.setDescription("Project Description " + uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(project));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithTooLongReference() {
        String uuid = UUID.randomUUID().toString();
        String ref = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String name = "Project" + uuid;
        String description = "Project Description " + uuid;
        Project project = new Project();
        project.setName(name);
        project.setReference(ref);
        project.setDescription("Project Description " + uuid);
        project = projectRepository.save(project);
        assertTrue(project.getId() > 0);
        assertEquals(name, project.getName());
        assertEquals(ref, project.getReference());
        assertEquals(description, project.getDescription());

        project.setName(name + "1");
        project.setReference(ref + "1");
        Project finalProject = project;
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(finalProject));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewProjectWithDuplicateReference() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("Project Description " + uuid);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals(project.getDescription(), result.getDescription());

        Project anotherProject = new Project();
        anotherProject.setReference("Project" + uuid);
        anotherProject.setName("Project name 1 " + uuid);
        anotherProject.setDescription("Project Description 1 " + uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> projectRepository.save(anotherProject));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateNewProjectWithoutDescription() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertNull(result.getDescription());
    }

    @Test
    void shouldCreateNewProjectWithNullDescription() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription(null);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertNull(result.getDescription());
    }

    @Test
    void shouldCreateNewProjectWithEmptyDescription() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("");
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals("", result.getDescription());
    }

    @Test
    void shouldNotCreateNewProjectWithTooLongDescription() {
        String uuid = UUID.randomUUID().toString();
        String description = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String name = "Project" + uuid;
        String ref = "Project Reference " + uuid;
        Project project = new Project();
        project.setName(name);
        project.setReference(ref);
        project.setDescription(description);
        project = projectRepository.save(project);
        assertTrue(project.getId() > 0);
        assertEquals(name, project.getName());
        assertEquals(ref, project.getReference());
        assertEquals(description, project.getDescription());

        project.setName(name + "1");
        project.setReference(ref + "1");
        project.setDescription(description + "1");
        Project finalProject = project;
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRepository.save(finalProject));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateNewProjectWithDuplicateDescription() {
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("Project Description " + uuid);
        Project result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals(project.getDescription(), result.getDescription());

        project.setReference("Project 1" + uuid);
        project.setName("Project name 1 " + uuid);
        project.setDescription("Project Description " + uuid);
        result = projectRepository.save(project);
        assertTrue(result.getId() > 0);
        assertEquals(project.getName(), result.getName());
        assertEquals(project.getReference(), result.getReference());
        assertEquals(project.getDescription(), result.getDescription());
    }
}