package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.ComparisonResult;
import me.shenderov.visual.entities.dao.Image;

import java.io.IOException;

public interface ImageComparisonService {

    ComparisonResult doCompare(Image baseImage, Image actualImage) throws IOException;

}
