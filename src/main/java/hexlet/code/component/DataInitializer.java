package hexlet.code.component;

import hexlet.code.DTO.UsersDTO.UserCreateDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private Faker faker;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userCreate = new UserCreateDTO();
        userCreate.setEmail(email);
        userCreate.setPassword("qwerty");
        userService.createAndReturnUser(userCreate);
        var tsStatus = new TaskStatus();
        tsStatus.setName("Draft");
        tsStatus.setSlug("draft");
        taskStatusRepository.save(tsStatus);
        var tsStatus2 = new TaskStatus();
        tsStatus2.setName("ToReview");
        tsStatus2.setSlug("to_review");
        taskStatusRepository.save(tsStatus2);
        var tsStatus3 = new TaskStatus();
        tsStatus3.setName("ToBeFixed");
        tsStatus3.setSlug("to_be_fixed");
        taskStatusRepository.save(tsStatus3);
        var tsStatus4 = new TaskStatus();
        tsStatus4.setName("ToPublish");
        tsStatus4.setSlug("to_publish");
        taskStatusRepository.save(tsStatus4);
        var tsStatus5 = new TaskStatus();
        tsStatus5.setName("Published");
        tsStatus5.setSlug("published");
        taskStatusRepository.save(tsStatus5);
    }
}
