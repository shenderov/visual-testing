package me.shenderov.visual.entities;

import org.springframework.web.multipart.MultipartFile;

public class CreateScreenshotWrapper {

    private MultipartFile image;
    private String reference;
    private String name;
    private Long projectId;
    private Long buildId;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBuildId() {
        return buildId;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

    @Override
    public String toString() {
        return "CreateScreenshotWrapper{" +
                "image=" + image +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", projectId=" + projectId +
                ", buildId=" + buildId +
                '}';
    }
}
