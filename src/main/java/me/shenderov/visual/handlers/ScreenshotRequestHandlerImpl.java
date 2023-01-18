package me.shenderov.visual.handlers;

import me.shenderov.visual.entities.ComparisonResult;
import me.shenderov.visual.entities.CreateScreenshotWrapper;
import me.shenderov.visual.entities.ScreenshotInfo;
import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.entities.dao.Screenshot;
import me.shenderov.visual.enums.ScreenshotStatus;
import me.shenderov.visual.interfaces.ImageComparisonService;
import me.shenderov.visual.interfaces.ImageStorageService;
import me.shenderov.visual.interfaces.ScreenshotRequestHandler;
import me.shenderov.visual.repositories.BuildRepository;
import me.shenderov.visual.repositories.ProjectRepository;
import me.shenderov.visual.repositories.ScreenshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ScreenshotRequestHandlerImpl implements ScreenshotRequestHandler {

    private final ProjectRepository projectRepository;
    private final BuildRepository buildRepository;
    private final ScreenshotRepository screenshotRepository;
    private final ImageStorageService imageStorageService;
    private final ImageComparisonService comparisonService;

    @Autowired
    public ScreenshotRequestHandlerImpl(ProjectRepository projectRepository, BuildRepository buildRepository, ScreenshotRepository screenshotRepository, ImageStorageService imageStorageService, ImageComparisonService comparisonService) {
        this.projectRepository = projectRepository;
        this.buildRepository = buildRepository;
        this.screenshotRepository = screenshotRepository;
        this.imageStorageService = imageStorageService;
        this.comparisonService = comparisonService;
    }

    @Override
    public ScreenshotInfo createScreenshot(CreateScreenshotWrapper wrapper) {
        Optional<Project> project = projectRepository.findById(wrapper.getProjectId());
        if(project.isEmpty()){
            throw new RuntimeException("Project not found");
        }
        Optional<Build> build = buildRepository.findById(wrapper.getBuildId());
        if(build.isEmpty()){
            throw new RuntimeException("Build not found");
        }
        Optional<Screenshot> screenshotOptional = screenshotRepository.findTopByProjectAndNameOrderByBuildDesc(project.get(), wrapper.getName());
        Image image = imageStorageService.save(wrapper.getImage());
        Screenshot screenshot = new Screenshot();
        screenshot.setReference(wrapper.getReference());
        screenshot.setName(wrapper.getName());
        screenshot.setProject(project.get());
        screenshot.setBuild(build.get());
        screenshot.setActualImage(image);
        if(screenshotOptional.isEmpty()){
            screenshot.setBaseImage(image);
            screenshot.setDiffImage(image);
            screenshot.setDiffRate(0F);
            screenshot.setStatus(ScreenshotStatus.NEW);
        }else {
            Screenshot previousScreenshot = screenshotOptional.get();
            Image baseImage = previousScreenshot.getBaseImage();
            screenshot.setBaseImage(baseImage);
            ComparisonResult result;
            try {
                result = comparisonService.doCompare(baseImage, image);
                screenshot.setDiffImage(result.getDiffImage());
                screenshot.setDiffRate(result.getDiffRate());
            } catch (IOException e) {
                throw new RuntimeException("Can not compare provided images");
            }
            if (result.getDiffRate() == 0D) {
                screenshot.setStatus(ScreenshotStatus.AUTO_APPROVED);
            } else if(result.getSizeMismatch()) {
                screenshot.setStatus(ScreenshotStatus.SIZE_MISMATCH);
            } else {
                screenshot.setStatus(ScreenshotStatus.PENDING_APPROVAL);
            }
        }
        screenshot = screenshotRepository.save(screenshot);
        return new ScreenshotInfo(screenshot);
    }

    @Override
    public Set<ScreenshotInfo> getScreenshotList(Long buildId) {
        Optional<Build> build = buildRepository.findById(buildId);
        if(build.isEmpty()){
            throw new RuntimeException("Build not found!");
        }
        Iterable<Screenshot> samples = screenshotRepository.findAllByBuild(build.get());
        Set<ScreenshotInfo> screenshotInfos = new HashSet<>();
        samples.forEach(sample -> screenshotInfos.add(new ScreenshotInfo(sample)));
        return screenshotInfos;
    }
}
