package me.shenderov.visual.handlers;

import me.shenderov.visual.entities.CreateProjectWrapper;
import me.shenderov.visual.entities.ProjectInfo;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.interfaces.ProjectRequestHandler;
import me.shenderov.visual.repositories.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProjectRequestHandlerImpl implements ProjectRequestHandler {

    private final ProjectRepository projectRepository;

    public ProjectRequestHandlerImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectInfo createProject(CreateProjectWrapper project) {
        Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setReference(project.getReference());
        newProject.setDescription(project.getDescription());
        newProject = projectRepository.save(newProject);
        return new ProjectInfo(newProject);
    }

    @Override
    public Set<ProjectInfo> getProjectList() {
        Iterable<Project> projects = projectRepository.findAll();
        Set<ProjectInfo> projectInfos = new HashSet<>();
        projects.forEach(project -> projectInfos.add(new ProjectInfo(project)));
        return projectInfos;
    }
}
