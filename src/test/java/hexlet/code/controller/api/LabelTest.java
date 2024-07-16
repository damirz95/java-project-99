package hexlet.code.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
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
    private Label testLabel;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        labelRepository.save(testLabel);
    }

    @Test
    public void indexTest() throws Exception {
        mockMvc.perform(get("/api/labels").with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void showTest() throws Exception {
        mockMvc.perform(get("/api/labels/" + testLabel.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        var label = Instancio.of(modelGenerator.getLabelModel()).create();
        var request = post("/api/labels").with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(label));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
        var labelC = labelRepository.findByName(label.getName()).get();

        assertThat(labelC.getName()).isEqualTo(label.getName());
    }

    @Test
    public void updateTest() throws Exception {
        var data = new HashMap<>();
        data.put("name", "exampleName");
        var request = put("/api/labels/" + testLabel.getId()).with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsBytes(data));
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var label = labelRepository.findById(testLabel.getId()).get();

        assertThat(label.getName()).isEqualTo("exampleName");
    }

    @Test
    public void deleteTest() throws Exception {
        var request = delete("/api/labels/" + testLabel.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(labelRepository.findByName(testLabel.getName())).isNotPresent();
    }
}
