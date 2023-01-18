package me.shenderov.visual.entities;

import me.shenderov.visual.entities.dao.Image;

import java.io.File;

public class ComparisonResult {

    private Image diffImage;
    private Float diffRate;
    private Boolean sizeMismatch;

    public ComparisonResult(Image diffImage, Float diffRate, Boolean sizeMismatch) {
        this.diffImage = diffImage;
        this.diffRate = diffRate;
        this.sizeMismatch = sizeMismatch;
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

    public Boolean getSizeMismatch() {
        return sizeMismatch;
    }

    public void setSizeMismatch(Boolean sizeMismatch) {
        this.sizeMismatch = sizeMismatch;
    }
}
