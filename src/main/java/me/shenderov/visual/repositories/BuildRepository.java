package me.shenderov.visual.repositories;

import me.shenderov.visual.entities.dao.Build;
import me.shenderov.visual.entities.dao.Project;
import org.springframework.data.repository.CrudRepository;

public interface BuildRepository extends CrudRepository<Build, Long> {

    Iterable<Build> findAllByProject(Project project);

}
