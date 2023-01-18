package me.shenderov.visual.entities;

import org.springframework.core.io.Resource;

public class ResourceWrapper {
    private Resource resource;
    private String filename;

    public ResourceWrapper(Resource resource, String filename) {
        this.resource = resource;
        this.filename = filename;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "ResourceWrapper{" +
                "resource=" + resource +
                ", filename='" + filename + '\'' +
                '}';
    }
}
