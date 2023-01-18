package me.shenderov.visual.apis;

import me.shenderov.visual.entities.CreateScreenshotWrapper;
import me.shenderov.visual.entities.ScreenshotInfo;
import me.shenderov.visual.interfaces.ScreenshotRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screenshot")
public class ScreenshotRestController {

    private final ScreenshotRequestHandler screenshotRequestHandler;

    @Autowired
    public ScreenshotRestController(ScreenshotRequestHandler screenshotRequestHandler) {
        this.screenshotRequestHandler = screenshotRequestHandler;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ScreenshotInfo create(@ModelAttribute CreateScreenshotWrapper wrapper) {
        return screenshotRequestHandler.createScreenshot(wrapper);
    }
}
