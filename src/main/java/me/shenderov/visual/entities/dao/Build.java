package me.shenderov.visual.entities.dao;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "project_id", "name" }),
        @UniqueConstraint(columnNames = { "project_id", "reference" }) })
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "build")
    @SequenceGenerator(name = "build", sequenceName = "build_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "Reference value should not be empty")
    @NotNull(message = "Reference value should not be null")
    @Size(max = 100, message = "Reference value length should not extend 100")
    @Column(name = "reference")
    private String reference;

    @NotEmpty(message = "Name value should not be empty")
    @NotNull(message = "Name value should not be null")
    @Size(min = 1, max = 100, message = "Name value should be in range of 1 to 100")
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    public Build() {
    }

    public Build(Long id, String reference, String name) {
        this.id = id;
        this.reference = reference;
        this.name = name;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return id.equals(build.id) && reference.equals(build.reference) && name.equals(build.name) && project.equals(build.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, name, project);
    }
}
