package me.shenderov.visual.entities.dao;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project")
    @SequenceGenerator(name = "project", sequenceName = "project_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100, message = "Reference value length should not extend 100")
    @Column(name = "reference", unique = true)
    private String reference;

    @NotEmpty(message = "Name value should not be empty")
    @NotNull(message = "Name value should not be null")
    @Size(min = 1, max = 100, message = "Name value should be in range of 1 to 100")
    @Column(name = "name", unique = true)
    private String name;

    @Size(max = 500, message = "Description value length should not extend 100")
    @Column(name = "description")
    private String description;

    public Project() {
    }

    public Project(Long id, String reference, String name, String description) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id.equals(project.id) && reference.equals(project.reference) && name.equals(project.name) && Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, name, description);
    }
}
