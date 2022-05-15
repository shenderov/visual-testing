package me.shenderov.visual.services;

import me.shenderov.visual.VisualTestingApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FileSystemImageStorageTest extends VisualTestingApplicationTests {

    @Test
    void init() {
    }

    @Test
    void save() throws Exception {
        ClassPathResource resource = new ClassPathResource("base.png", getClass());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "base.png",
                "image/png", resource.getInputStream().readAllBytes());
        mvc.perform(multipart("/image/upload").file(multipartFile))
                .andExpect(status().isCreated());
        then(this.imageStorageService).should().save(multipartFile);
    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void deleteAll() {
    }
}