package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.ResourceWrapper;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    void init();

    void save(MultipartFile file);

    ResourceWrapper get(Long id);

    ResourceWrapper get(String filename);

    void delete(Long id);

    void delete(String filename);

    void deleteAll();
}
