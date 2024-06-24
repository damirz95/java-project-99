/*
package hexlet.code.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class LabelTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private ModelGenerator modelGenerator;

    private JwtRequestPostProcessor token;
    private Task testTask;
    private Label testLabel;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        labelRepository.save(testLabel);
    }

    @Test
    public void indexTest() throws Exception {
        mockMvc.perform(get("/api/labels").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void showTest() throws Exception {
        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        var testTask2 = Instancio.of(modelGenerator.getTaskModel()).create();
        labelRepository.save(testLabel);

        var request = get("/api/tasks/" + testTask.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
}
*/
