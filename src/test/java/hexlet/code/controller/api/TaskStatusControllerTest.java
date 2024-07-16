package hexlet.code.controller.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusControllerTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModelGenerator modelGenerator;
    private JwtRequestPostProcessor token;

    private TaskStatus testStatusTask;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testStatusTask = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testStatusTask);

    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/task_statuses").with(jwt()))
                .andExpect(status().isOk());

    }

    @Test
    public void testShow() throws Exception {
        taskStatusRepository.save(testStatusTask);
        mockMvc.perform(get("/api/task_statuses/" + testStatusTask.getId()).with(jwt()))
                .andExpect(status().isOk());

        var status = taskStatusRepository.findById(testStatusTask.getId()).get();
        assertThat(status.getSlug()).isEqualTo(testStatusTask.getSlug());

    }
    @Test
    public void testCreate() throws Exception {
        testStatusTask = Instancio.of(modelGenerator.getTaskStatusModel()).create();

        var request = post("/api/task_statuses").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(testStatusTask));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var taskStatus = taskStatusRepository.findBySlug(testStatusTask.getSlug()).get();

        assertNotNull(taskStatus);
        assertThat(taskStatus.getSlug()).isEqualTo(testStatusTask.getSlug());
        assertThat(taskStatus.getName()).isEqualTo(testStatusTask.getName());
    }
    @Test
    public void testUpdate() throws Exception {
        var id = testStatusTask.getId();

        testStatusTask = Instancio.of(modelGenerator.getTaskStatusModel()).create();

        var data = new HashMap<>();
        data.put("name", testStatusTask.getName());
        data.put("slug", testStatusTask.getSlug());

        var request = put("/api/task_statuses/" + id).with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isOk());

        var status = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));

        assertThat(status.getName()).isEqualTo(testStatusTask.getName());
        assertThat(status.getSlug()).isEqualTo(testStatusTask.getSlug());
    }

    @Test
    public void deleteTest() throws Exception {
        var request = delete("/api/task_statuses/" + testStatusTask.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(taskStatusRepository.findById(testStatusTask.getId())).isNotPresent();
    }
}
