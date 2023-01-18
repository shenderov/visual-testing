package me.shenderov.visual.services;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import me.shenderov.visual.entities.ComparisonResult;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.interfaces.ImageComparisonService;
import me.shenderov.visual.interfaces.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

@Service
public class ImageComparisonServiceImpl implements ImageComparisonService {

    private final ImageStorageService imageStorageService;

    @Autowired
    public ImageComparisonServiceImpl(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @Override
    public ComparisonResult doCompare(Image baseImage, Image actualImage) {
        BufferedImage bufferedBaseImage = imageStorageService.getBufferedImage(baseImage.getId());
        BufferedImage bufferedActualImage = imageStorageService.getBufferedImage(actualImage.getId());
        ImageComparisonResult comparisonResult = new ImageComparison(bufferedBaseImage, bufferedActualImage).compareImages();
        Image diffImage = null;
        boolean sizeMismatch = false;
        float diff = comparisonResult.getDifferencePercent();
        if(comparisonResult.getImageComparisonState() == ImageComparisonState.MATCH){
            diffImage = actualImage;
        } else if (comparisonResult.getImageComparisonState() == ImageComparisonState.MISMATCH) {
            File tmpDiffImage;
            try {
                tmpDiffImage = imageStorageService.createTmpFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot create temp file");
            }
            ImageComparisonUtil.saveImage(tmpDiffImage, comparisonResult.getResult());
            diffImage = imageStorageService.save(tmpDiffImage, getDiffFilename(actualImage));
            imageStorageService.deleteTmpFile(tmpDiffImage);
        } else {
            sizeMismatch = true;
            diff = 0;
        }
        return new ComparisonResult(diffImage, diff, sizeMismatch);
    }

    private String getDiffFilename(Image image){
        int indexOfExtension = image.getFilename().lastIndexOf(".");
        String extension = image.getFilename().substring(indexOfExtension);
        String name = image.getFilename().substring(0, indexOfExtension);
        return name + ".diff" +  extension;
    }
}
