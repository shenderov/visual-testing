package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.BuildInfo;
import me.shenderov.visual.entities.CreateBuildWrapper;

import java.util.Set;

public interface BuildRequestHandler {
    BuildInfo createBuild(CreateBuildWrapper wrapper);

    Set<BuildInfo> getBuildList(Long projectId);
}
