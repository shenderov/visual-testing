package me.shenderov.visual.entities;

import me.shenderov.visual.entities.dao.Project;

public class ProjectInfo {

    private Long id;
    private String reference;
    private String name;
    private String description;
    private Integer numberOfBuilds;

    public ProjectInfo() {
    }

    public ProjectInfo(Project project) {
        this.id = project.getId();
        this.reference = project.getReference();
        this.name = project.getName();
        this.description = project.getDescription();
        this.numberOfBuilds = 0;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfBuilds() {
        return numberOfBuilds;
    }

    public void setNumberOfBuilds(Integer numberOfBuilds) {
        this.numberOfBuilds = numberOfBuilds;
    }

}
