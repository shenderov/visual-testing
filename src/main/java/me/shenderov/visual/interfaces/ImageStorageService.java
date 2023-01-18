package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.ResourceWrapper;
import me.shenderov.visual.entities.dao.Image;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageStorageService {
    void init();

    Image save(MultipartFile file);

    Image save(File file);

    Image save(File file, String filename);

    ResourceWrapper get(Long id);

    BufferedImage getBufferedImage(Long id);

    File createTmpFile() throws IOException;

    void deleteTmpFile(File tmpFile);
}
