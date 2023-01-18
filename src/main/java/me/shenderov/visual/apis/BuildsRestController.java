package me.shenderov.visual.apis;

import me.shenderov.visual.entities.BuildInfo;
import me.shenderov.visual.entities.CreateBuildWrapper;
import me.shenderov.visual.interfaces.BuildRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/build")
public class BuildsRestController {

    private final BuildRequestHandler buildRequestHandler;

    @Autowired
    public BuildsRestController(BuildRequestHandler buildRequestHandler) {
        this.buildRequestHandler = buildRequestHandler;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BuildInfo createBuild(@RequestBody CreateBuildWrapper wrapper) {
        return buildRequestHandler.createBuild(wrapper);
    }

    @GetMapping("/get/{project_id:.+}")
    @ResponseStatus(HttpStatus.OK)
    public Set<BuildInfo> getAllBuilds(@PathVariable Long project_id) {
        return buildRequestHandler.getBuildList(project_id);
    }
}
