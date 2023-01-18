package me.shenderov.visual.handlers;

import me.shenderov.visual.entities.BuildInfo;
import me.shenderov.visual.entities.CreateBuildWrapper;
import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.interfaces.BuildRequestHandler;
import me.shenderov.visual.repositories.BuildRepository;
import me.shenderov.visual.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class BuildRequestHandlerImpl implements BuildRequestHandler {

    private final BuildRepository buildRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public BuildRequestHandlerImpl(BuildRepository buildRepository, ProjectRepository projectRepository) {
        this.buildRepository = buildRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public BuildInfo createBuild(CreateBuildWrapper wrapper) {
        Optional<Project> project = projectRepository.findById(wrapper.getProjectId());
        if(project.isEmpty()){
            throw new RuntimeException("Project is not found");
        }
        Build build = new Build();
        build.setReference(wrapper.getReference());
        build.setName(wrapper.getName());
        build.setProject(project.get());
        build = buildRepository.save(build);
        return new BuildInfo(build);
    }

    @Override
    public Set<BuildInfo> getBuildList(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new RuntimeException("Project not found!");
        }
        Iterable<Build> builds = buildRepository.findAllByProject(project.get());
        Set<BuildInfo> buildInfos = new HashSet<>();
        builds.forEach(build -> buildInfos.add(new BuildInfo(build)));
        return buildInfos;
    }
}
