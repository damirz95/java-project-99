package hexlet.code.component;

import hexlet.code.DTO.UsersDTO.UserCreateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    private Faker faker;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userCreate = new UserCreateDTO();
        userCreate.setEmail(email);
        userCreate.setPassword("qwerty");
        userService.createAndReturnUser(userCreate);


        Map<String, String> statusMap = new HashMap<>();

        statusMap.put("Draft", "draft");
        statusMap.put("ToReview", "to_review");
        statusMap.put("ToBeFixed", "to_be_fixed");
        statusMap.put("ToPublish", "to_publish");
        statusMap.put("Published", "published");
        for (String key: statusMap.keySet()) {
            var tsStatus = new TaskStatus();
            tsStatus.setName(key);
            tsStatus.setSlug(statusMap.get(key));
            taskStatusRepository.save(tsStatus);
        }

        List<String> labelsList = new ArrayList<>();
        labelsList.add("bug");
        labelsList.add("feature");

        for (var label : labelsList) {
            var tsLabel = new Label();
            tsLabel.setName(label);
            labelRepository.save(tsLabel);
        }


        var statuses = taskStatusRepository.findAll();
        var labels = labelRepository.findAll();
        for (int i = 0; i < 5; i++) {
            var task = new Task();
            task.setStatus(statuses.get(i));
            task.setName(faker.name().name());
            task.setDescription(faker.lorem().paragraph());
            task.setLabels(Set.of(labels.get(0)));
            task.setAssignee(userRepository.findByEmail("hexlet@example.com").get());
            taskRepository.save(task);
        }
    }
}
