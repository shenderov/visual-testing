package me.shenderov.visual.entities;

import me.shenderov.visual.enums.ImageType;

import javax.persistence.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private ImageType type;
    private String filename;
    private String checksum;

    public Image() {
    }

    public Image(Long id, ImageType type, String filename, String checksum) {
        this.id = id;
        this.type = type;
        this.filename = filename;
        this.checksum = checksum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", type=" + type +
                ", filename='" + filename + '\'' +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
