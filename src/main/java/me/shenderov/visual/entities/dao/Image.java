package me.shenderov.visual.entities.dao;

import me.shenderov.visual.enums.ImageType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image")
    @SequenceGenerator(name = "image", sequenceName = "image_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private ImageType type;

    @NotEmpty(message = "Filename value should not be empty")
    @NotNull(message = "Filename value should not be null")
    @Size(max = 100, message = "Filename value length should not extend 100")
    @Column(name = "filename")
    private String filename;

    @NotEmpty(message = "Checksum value should not be empty")
    @NotNull(message = "Checksum value should not be null")
    @Size(max = 100, message = "Checksum value length should not extend 100")
    @Column(name = "checksum")
    private String checksum;

    @Column(name = "size", nullable = false)
    private Long size;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id.equals(image.id) && type == image.type && filename.equals(image.filename) && checksum.equals(image.checksum) && size.equals(image.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, filename, checksum, size);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", type=" + type +
                ", filename='" + filename + '\'' +
                ", checksum='" + checksum + '\'' +
                ", size=" + size +
                '}';
    }
}
