package me.shenderov.visual.entities;

import me.shenderov.visual.entities.dao.Screenshot;
import me.shenderov.visual.enums.ScreenshotStatus;

public class ScreenshotInfo {

    private Long id;
    private String reference;
    private String name;
    private Long projectId;
    private Long buildId;
    private Long baseImageId;
    private Long actualImageId;
    private Long diffImageId;
    private Float diffRate;
    private ScreenshotStatus status;

    public ScreenshotInfo() {
    }

    public ScreenshotInfo(Long id, String reference, String name, Long projectId, Long buildId, Long baseImageId, Long actualImageId, Long diffImageId, Float diffRate, ScreenshotStatus status) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.projectId = projectId;
        this.buildId = buildId;
        this.baseImageId = baseImageId;
        this.actualImageId = actualImageId;
        this.diffImageId = diffImageId;
        this.diffRate = diffRate;
        this.status = status;
    }

    public ScreenshotInfo(Screenshot screenshot) {
        this.id = screenshot.getId();
        this.reference = screenshot.getReference();
        this.name = screenshot.getName();
        this.projectId = screenshot.getProject().getId();
        this.buildId = screenshot.getBuild().getId();
        this.baseImageId = screenshot.getBaseImage().getId();
        this.actualImageId = screenshot.getActualImage().getId();
        this.diffImageId = screenshot.getDiffImage().getId();
        this.diffRate = screenshot.getDiffRate();
        this.status = screenshot.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getBaseImageId() {
        return baseImageId;
    }

    public void setBaseImageId(Long baseImageId) {
        this.baseImageId = baseImageId;
    }

    public Long getActualImageId() {
        return actualImageId;
    }

    public void setActualImageId(Long actualImageId) {
        this.actualImageId = actualImageId;
    }

    public Long getDiffImageId() {
        return diffImageId;
    }

    public void setDiffImageId(Long diffImageId) {
        this.diffImageId = diffImageId;
    }

    public Float getDiffRate() {
        return diffRate;
    }

    public void setDiffRate(Float diffRate) {
        this.diffRate = diffRate;
    }

    public ScreenshotStatus getStatus() {
        return status;
    }

    public void setStatus(ScreenshotStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScreenshotInfo{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", projectId=" + projectId +
                ", buildId=" + buildId +
                ", baseImageId=" + baseImageId +
                ", actualImageId=" + actualImageId +
                ", diffImageId=" + diffImageId +
                ", diffRate=" + diffRate +
                ", status=" + status +
                '}';
    }
}
