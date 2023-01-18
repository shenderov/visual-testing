package me.shenderov.visual;

import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.enums.ImageType;
import me.shenderov.visual.interfaces.*;
import me.shenderov.visual.repositories.BuildRepository;
import me.shenderov.visual.repositories.ImageRepository;
import me.shenderov.visual.repositories.ProjectRepository;
import me.shenderov.visual.repositories.ScreenshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.UUID;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {VisualTestingApplication.class})
public class VisualTestingApplicationTests {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ImageStorageService imageStorageService;

    @Autowired
    protected ImageComparisonService imageComparisonService;

    @Autowired
    protected ImageRepository imageRepository;

    @Autowired
    protected ScreenshotRepository screenshotRepository;

    @Autowired
    protected BuildRepository buildRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected ProjectRequestHandler projectRequestHandler;

    @Autowired
    protected BuildRequestHandler buildRequestHandler;

    @Autowired
    protected ScreenshotRequestHandler screenshotRequestHandler;

    private final ClassLoader classLoader = getClass().getClassLoader();

    protected Project createProject(){
        String uuid = UUID.randomUUID().toString();
        Project project = new Project();
        project.setReference("Project" + uuid);
        project.setName("Project name " + uuid);
        project.setDescription("Project Description " + uuid);
        return projectRepository.save(project);
    }

    protected Build createBuild(Project project){
        String uuid = UUID.randomUUID().toString();
        Build build = new Build();
        build.setReference("Build ref " + uuid);
        build.setName("Build name " + uuid);
        build.setProject(project);
        return buildRepository.save(build);
    }

    protected Image createImage(){
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(uuid + ".png");
        image.setChecksum(uuid);
        image.setSize(1000L);
        return imageRepository.save(image);
    }

    protected MultipartFile createMultipartMockImageFromResource(String filename){
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        try {
            return new MockMultipartFile("file", filename, "image/png", new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
