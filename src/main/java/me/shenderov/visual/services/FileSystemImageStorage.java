package me.shenderov.visual.services;

import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.entities.ResourceWrapper;
import me.shenderov.visual.enums.ImageType;
import me.shenderov.visual.interfaces.ImageStorageService;
import me.shenderov.visual.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemImageStorage implements ImageStorageService {

    private final Path storageDir;
    private final Path tmpDir;
    private final ImageRepository imageRepository;

    @Autowired
    public FileSystemImageStorage(ImageRepository imageRepository) {
        //TODO move to properties
        this.storageDir = Paths.get("src/main/resources/storage");
        this.tmpDir = this.storageDir.resolve("tmp");
        this.imageRepository = imageRepository;
        this.init();
    }

    @Override
    public void init() {
        try {
            if(!Files.isDirectory(storageDir)){
                Files.createDirectory(storageDir);
            }
            if(!Files.isDirectory(tmpDir)){
                Files.createDirectory(tmpDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public Image save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            String checksum = getChecksum(file.getInputStream());
            Image image = new Image();
            image.setFilename(file.getOriginalFilename());
            image.setType(ImageType.IMAGE);
            image.setChecksum(checksum);
            image.setSize(file.getSize());
            saveFile(file.getInputStream(), checksum);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Image save(File file) {
        String filename = null;
        if(file != null){
            filename = file.getName();
        }
        return save(file, filename);
    }

    @Override
    public Image save(File file, String filename) {
        try {
            if (file == null || !file.exists() || !file.isFile() || file.length() == 0) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            String checksum = getChecksum(new FileInputStream(file));
            Image image = new Image();
            image.setFilename(filename);
            image.setType(ImageType.IMAGE);
            image.setChecksum(checksum);
            image.setSize(file.length());
            saveFile(new FileInputStream(file), checksum);
            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    @Override
    public ResourceWrapper get(Long id) {
        try {
            ResourceWrapper resource = getFile(id);
            if(resource.getResource().exists() || resource.getResource().isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + id);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + id, e);
        }
    }

    @Override
    public BufferedImage getBufferedImage(Long id) {
        try {
            Resource resource = get(id).getResource();
            return ImageIO.read(resource.getFile());
        } catch (IOException e) {
           throw new RuntimeException("Image not found");
        }
    }

    @Override
    public File createTmpFile() throws IOException {
        return Files.createFile(tmpDir.resolve(UUID.randomUUID().toString())).toFile();
    }

    @Override
    public void deleteTmpFile(File tmpFile) {
        boolean delete = tmpFile.delete();
        if(!delete){
            //TODO replace with logger
            System.out.println("Cannot delete temp file");
        }
    }

    private Long getImageId(String filename){
        Optional<Image> imageOpt = imageRepository.findImageByFilename(filename);
        if(imageOpt.isPresent()){
            Image image = imageOpt.get();
            return image.getId();
        }
        return null;
    }

    private String getChecksum(InputStream is) {
        if(is == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DigestInputStream dis = new DigestInputStream(is, md);
            byte[] buffer = new byte[1024 * 8];
            while(dis.read(buffer) != -1) ;
            dis.close();
            byte[] raw = md.digest();
            BigInteger bigInt = new BigInteger(1, raw);
            StringBuilder hash = new StringBuilder(bigInt.toString(16));
            while(hash.length() < 32 ){
                hash.insert(0, '0');
            }
            return hash.toString();
        } catch (Throwable t) {
            return null;
        }
    }

    private void saveFile(InputStream is, String checksum) throws IOException {
        Path folder = getFolderPath(checksum);
        Path file = getFilePath(checksum);
        if(!Files.isDirectory(folder)){
            Files.createDirectory(folder);
        }
        if(!Files.exists(file)){
            Files.copy(is, file);
        }
    }

    private void deleteFile(String checksum) {
        Path folder = getFolderPath(checksum);
        Path file = getFilePath(checksum);
        try {
        if(Files.exists(file)){
            Files.delete(file);
        }
        if(isDirectoryEmpty(folder)){
            Files.deleteIfExists(folder);
        }
        } catch (IOException e) {
            throw new RuntimeException("Can not delete a file");
        }
    }

    private Path getFilePath(String checksum) {
        return getFolderPath(checksum).resolve(checksum);
    }

    private Path getFolderPath(String checksum) {
        return this.storageDir.resolve(checksum.substring(0, 2));
    }

    private boolean isDirectoryEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return entries.findFirst().isEmpty();
            }
        }
        return false;
    }

    private ResourceWrapper getFile(Long id) throws IOException {
        Resource resource;
        ResourceWrapper wrapper;
        Optional<Image> imageOpt = imageRepository.findById(id);
        if(imageOpt.isPresent()){
            Image image = imageOpt.get();
            Path folder = this.storageDir.resolve(image.getChecksum().substring(0, 2));
            if(!Files.isDirectory(folder)){
                throw new RuntimeException("File not found");
            } else if (!Files.exists(folder.resolve(image.getChecksum()))){
                throw new RuntimeException("File not found");
            } else {
                resource = new UrlResource(folder.resolve(image.getChecksum()).toUri());
                wrapper = new ResourceWrapper(resource, image.getFilename());
            }
        } else {
            wrapper = null;
        }
        return wrapper;
    }

}
