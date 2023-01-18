package me.shenderov.visual.entities.dao;

import me.shenderov.visual.enums.ScreenshotStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
//@Table(uniqueConstraints = {
//                @UniqueConstraint(columnNames = { "project_id", "build_id", "name" }),
//                @UniqueConstraint(columnNames = { "project_id", "build_id", "reference" })})
public class Screenshot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screenshot")
    @SequenceGenerator(name = "screenshot", sequenceName = "screenshot_seq", allocationSize = 1)
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

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "build_id", nullable = false)
    private Build build;

    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "base_image_id", nullable = false, unique = true)
    private Image baseImage;

    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "actual_image_id", nullable = false, unique = true)
    private Image actualImage;

    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "diff_image_id", nullable = false, unique = true)
    private Image diffImage;

    @Column(name = "diff_rate", nullable = false)
    private Float diffRate;

    @Column(name = "status", nullable = false)
    private ScreenshotStatus status;

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

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public Image getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(Image baseImage) {
        this.baseImage = baseImage;
    }

    public Image getActualImage() {
        return actualImage;
    }

    public void setActualImage(Image actualImage) {
        this.actualImage = actualImage;
    }

    public Image getDiffImage() {
        return diffImage;
    }

    public void setDiffImage(Image diffImage) {
        this.diffImage = diffImage;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screenshot that = (Screenshot) o;
        return id.equals(that.id) && reference.equals(that.reference) && name.equals(that.name) && project.equals(that.project) && build.equals(that.build) && baseImage.equals(that.baseImage) && actualImage.equals(that.actualImage) && diffImage.equals(that.diffImage) && diffRate.equals(that.diffRate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, name, project, build, baseImage, actualImage, diffImage, diffRate, status);
    }
}
