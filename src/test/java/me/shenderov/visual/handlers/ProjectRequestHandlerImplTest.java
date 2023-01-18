package me.shenderov.visual.handlers;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.CreateProjectWrapper;
import me.shenderov.visual.entities.ProjectInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRequestHandlerImplTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateNewProject() {
        String name = "New Project";
        String ref = "new-project-reference";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals(ref, projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());
    }

    @Test
    void shouldNotCreateProjectWithoutName() {
        String ref = "createProjectWithoutName";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateProjectWithNullName() {
        String ref = "createProjectNullName";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName("");
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateProjectWithMaxNameSize() {
        String name = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        String ref = "createProjectMaxNameSize";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals(ref, projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        wrapper.setName(name + "1");
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateProjectWithNotUniqueName() {
        String name = "New Project Not Unique";
        String ref = "createProjectNotUniqueName";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals(ref, projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        wrapper.setReference("createProjectNotUniqueName1");
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateProjectWithoutReference() {
        String name = "New Project createProjectNoReference";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertNull(projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        String name1 = "New Project createProjectNoReference1";
        String description1 = "New Project description1";
        CreateProjectWrapper wrapper1 = new CreateProjectWrapper();
        wrapper1.setName(name1);
        wrapper1.setDescription(description1);
        ProjectInfo projectInfo1 = projectRequestHandler.createProject(wrapper1);
        assertEquals(name1, projectInfo1.getName());
        assertNull(projectInfo1.getReference());
        assertEquals(description1, projectInfo1.getDescription());
        assertEquals(0, projectInfo1.getNumberOfBuilds());
    }

    @Test
    void shouldCreateProjectWithNullReference() {
        String name = "New Project createProjectNullReference";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(null);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertNull(projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());
    }

    @Test
    void shouldCreateProjectWithEmptyReference() {
        String name = "New Project createProjectNullReference";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference("");
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals("", projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        String name1 = "New Project createProjectNullReference1";
        String description1 = "New Project description1";
        CreateProjectWrapper wrapper1 = new CreateProjectWrapper();
        wrapper.setName(name1);
        wrapper.setReference("");
        wrapper.setDescription(description1);
        ProjectInfo projectInfo1 = projectRequestHandler.createProject(wrapper1);
        assertEquals(name1, projectInfo1.getName());
        assertEquals("", projectInfo1.getReference());
        assertEquals(description1, projectInfo1.getDescription());
        assertEquals(0, projectInfo1.getNumberOfBuilds());
    }

    @Test
    void shouldCreateProjecWithtMaxRefSize() {
        String name = "New Project createProjectMaxRefSize";
        String ref = "nFjLuskEtHEVAs0twfCi3ZJA9lMlNNsKG6HzT5T9tle2AhRgTG7vGBVxYgVnxW5wyp86rGH6XCHRLwt4jDXbmMeGWidf7AIVrAcA";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals(ref, projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        wrapper.setName(name + "1");
        wrapper.setReference(ref + "1");
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateProjectWithNotUniqueReference() {
        String name = "New Project createProjectNotUniqueReference";
        String ref = "createProjectNotUniqueReference";
        String description = "New Project description";
        CreateProjectWrapper wrapper = new CreateProjectWrapper();
        wrapper.setName(name);
        wrapper.setReference(ref);
        wrapper.setDescription(description);
        ProjectInfo projectInfo = projectRequestHandler.createProject(wrapper);
        assertEquals(name, projectInfo.getName());
        assertEquals(ref, projectInfo.getReference());
        assertEquals(description, projectInfo.getDescription());
        assertEquals(0, projectInfo.getNumberOfBuilds());

        wrapper.setName(name + "1");
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> projectRequestHandler.createProject(wrapper));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }
}