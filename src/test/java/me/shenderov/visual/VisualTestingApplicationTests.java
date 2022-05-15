package me.shenderov.visual;

import me.shenderov.visual.interfaces.ImageStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {VisualTestingApplication.class})
public class VisualTestingApplicationTests {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected ImageStorageService imageStorageService;

    @Test
    void contextLoads() {
    }

}
