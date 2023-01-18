package me.shenderov.visual.repositories;

import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Project;
import me.shenderov.visual.entities.dao.Screenshot;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScreenshotRepository extends CrudRepository<Screenshot, Long> {

    Optional<Screenshot> findTopByProjectAndNameOrderByBuildDesc(Project project, String name);

    Iterable<Screenshot> findAllByBuild(Build build);
}
