package me.shenderov.visual.entities;

import me.shenderov.visual.entities.dao.Build;

import java.util.Objects;

public class BuildInfo {

    private Long id;
    private String reference;
    private String name;
    private Long projectId;
    private Integer numberOfSamples;

    public BuildInfo() {
    }

    public BuildInfo(Build build) {
        this.id = build.getId();
        this.reference = build.getReference();
        this.name = build.getName();
        this.projectId = build.getProject().getId();
        this.numberOfSamples = 0;
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

    public Integer getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(Integer numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildInfo buildInfo = (BuildInfo) o;
        return id.equals(buildInfo.id) && reference.equals(buildInfo.reference) && name.equals(buildInfo.name) && projectId.equals(buildInfo.projectId) && numberOfSamples.equals(buildInfo.numberOfSamples);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, name, projectId, numberOfSamples);
    }
}
