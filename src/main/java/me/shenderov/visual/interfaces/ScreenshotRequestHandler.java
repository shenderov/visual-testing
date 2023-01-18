package me.shenderov.visual.interfaces;

import me.shenderov.visual.entities.CreateScreenshotWrapper;
import me.shenderov.visual.entities.ScreenshotInfo;

import java.util.Set;

public interface ScreenshotRequestHandler {
    ScreenshotInfo createScreenshot(CreateScreenshotWrapper wrapper);

    Set<ScreenshotInfo> getScreenshotList(Long buildId);
}
