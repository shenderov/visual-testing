package me.shenderov.visual.apis;

import me.shenderov.visual.entities.ResourceWrapper;
import me.shenderov.visual.interfaces.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageRestController {

    private final ImageStorageService storageService;

    @Autowired
    public ImageRestController(ImageStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        storageService.save(file);
        return "You successfully uploaded " + file.getOriginalFilename() + "!";
    }

    @GetMapping("/download/{id:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadImageById(@PathVariable Long id) {
        ResourceWrapper file = storageService.get(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file.getResource());
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadImageByFilename(@RequestParam(name = "filename") String filename) {
        ResourceWrapper file = storageService.get(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file.getResource());
    }

    @GetMapping("/delete/{id:.+}")
    @ResponseBody
    public String deleteImageById(@PathVariable Long id) {
        storageService.delete(id);
        return "You successfully deleted file with ID: " + id + "!";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteImageById(@RequestParam(name = "filename") String filename) {
        storageService.delete(filename);
        return "You successfully deleted " + filename + "!";
    }

    @GetMapping("/delete/all")
    @ResponseBody
    public String deleteAllImages() {
        storageService.deleteAll();
        return "You successfully deleted all files!";
    }
}
