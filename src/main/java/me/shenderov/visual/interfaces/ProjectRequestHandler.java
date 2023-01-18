package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.CreateProjectWrapper;
import me.shenderov.visual.entities.ProjectInfo;

import java.util.Set;

public interface ProjectRequestHandler {
    ProjectInfo createProject(CreateProjectWrapper project);

//    ProjectInfo updateProject(CreateProjectWrapper project);
//
//    void deleteProject(long projectId);

    Set<ProjectInfo> getProjectList();
}
