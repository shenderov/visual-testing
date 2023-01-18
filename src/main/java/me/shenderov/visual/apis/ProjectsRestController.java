package me.shenderov.visual.apis;

import me.shenderov.visual.entities.CreateProjectWrapper;
import me.shenderov.visual.entities.ProjectInfo;
import me.shenderov.visual.interfaces.ProjectRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/project")
public class ProjectsRestController {

    private final ProjectRequestHandler projectRequestHandler;

    @Autowired
    public ProjectsRestController(ProjectRequestHandler projectRequestHandler) {
        this.projectRequestHandler = projectRequestHandler;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectInfo createProject(@RequestBody CreateProjectWrapper wrapper) {
        return projectRequestHandler.createProject(wrapper);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Set<ProjectInfo> getAllProjects() {
        return projectRequestHandler.getProjectList();
    }

}
