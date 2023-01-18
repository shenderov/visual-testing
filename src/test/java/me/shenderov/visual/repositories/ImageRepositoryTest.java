package me.shenderov.visual.repositories;

import me.shenderov.visual.VisualTestingApplicationTests;
import me.shenderov.visual.entities.dao.Image;
import me.shenderov.visual.enums.ImageType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTest extends VisualTestingApplicationTests {

    @Test
    void shouldCreateAndFindNewImage() {
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + ".png";
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(filename);
        image.setChecksum(uuid);
        Image result = imageRepository.save(image);
        assertTrue(result.getId() > 0);
        assertEquals(image, result);
        Optional<Image> resultOptional = imageRepository.findImageByFilename(filename);
        if(resultOptional.isPresent()){
            assertEquals(result, resultOptional.get());
        } else {
            fail("Image file wasn't found");
        }
    }

    @Test
    void shouldNotCreateNewImageWithoutFilename() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setChecksum(uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithNullFilename() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(null);
        image.setChecksum(uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithEmptyFilename() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename("");
        image.setChecksum(uuid);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithTooLongFilename() {
        String uuid = UUID.randomUUID().toString();
        String filename = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(filename);
        image.setChecksum(uuid);
        Image result = imageRepository.save(image);
        assertTrue(result.getId() > 0);
        assertEquals(image, result);

        Image anotherImage = new Image();
        anotherImage.setType(ImageType.IMAGE);
        anotherImage.setFilename(filename + "1");
        anotherImage.setChecksum(uuid);

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(anotherImage));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithoutChecksum() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setFilename(uuid + ".png");
        image.setType(ImageType.IMAGE);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithNullChecksum() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(uuid + ".png");
        image.setChecksum(null);
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithEmptyChecksum() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(uuid + ".png");
        image.setChecksum("");
        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(image));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithTooLongChecksum() {
        String uuid = UUID.randomUUID().toString();
        String checksum = "Q6aAcpKPWhUpsxh1DoQ4RyTWjROXjGpnrnRMJmNMn1SyHnAirGmIsDvAzjisHc0grK7Ka2qdmcx4TsjS3i4YN9avTRXXeICmFYv0";
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(uuid + ".png");
        image.setChecksum(checksum);
        Image result = imageRepository.save(image);
        assertTrue(result.getId() > 0);
        assertEquals(image, result);

        Image anotherImage = new Image();
        anotherImage.setType(ImageType.IMAGE);
        anotherImage.setFilename(uuid + ".png");
        anotherImage.setChecksum(checksum + "1");

        TransactionSystemException exception = assertThrows(TransactionSystemException.class, () -> imageRepository.save(anotherImage));
        String expectedMessage = "Could not commit JPA transaction";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateImagesWithTheSameFilenameChecksumAndType() {
        String uuid = UUID.randomUUID().toString();
        Image image1 = new Image();
        image1.setType(ImageType.IMAGE);
        image1.setFilename(uuid + ".png");
        image1.setChecksum(uuid);
        Image result1 = imageRepository.save(image1);
        assertTrue(result1.getId() > 0);
        assertEquals(image1, result1);

        Image image2 = new Image();
        image2.setType(ImageType.IMAGE);
        image2.setFilename(uuid + ".png");
        image2.setChecksum(uuid);
        Image result2 = imageRepository.save(image2);
        assertTrue(result2.getId() > 0);
        assertEquals(image2, result2);

        assertNotEquals(result1, result2);
    }

    @Test
    void shouldNotCreateNewImageWithoutType() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setFilename(uuid + ".png");
        image.setChecksum(uuid);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> imageRepository.save(image));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldNotCreateNewImageWithNullType() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setFilename(uuid + ".png");
        image.setChecksum(uuid);
        image.setType(null);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> imageRepository.save(image));
        String expectedMessage = "ConstraintViolationException";
        String actualMessage = exception.getMessage();
        assert actualMessage != null;
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateTwoImagesWithTheSameChecksumFilenameAndType() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setType(ImageType.IMAGE);
        image.setFilename(uuid + ".png");
        image.setChecksum(uuid);
        Image result = imageRepository.save(image);
        assertTrue(result.getId() > 0);
        assertEquals(image, result);

        Image image1 = new Image();
        image1.setType(ImageType.IMAGE);
        image1.setFilename(uuid + ".png");
        image1.setChecksum(uuid);
        Image result1 = imageRepository.save(image1);
        assertTrue(result1.getId() > result.getId());
        assertEquals(image1, result1);
    }
}