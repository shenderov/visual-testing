package me.shenderov.visual.repositories;

import me.shenderov.visual.entities.dao.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
