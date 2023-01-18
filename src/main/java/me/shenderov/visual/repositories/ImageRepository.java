package me.shenderov.visual.repositories;

import me.shenderov.visual.entities.dao.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {

    Optional<Image> findImageByFilename(String filename);

}
