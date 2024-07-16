package hexlet.code.controller.api;

import hexlet.code.DTO.TasksDTO.TaskCreateDTO;
import hexlet.code.DTO.TasksDTO.TaskDTO;
import hexlet.code.DTO.TasksDTO.TaskParamsDTO;
import hexlet.code.DTO.TasksDTO.TaskUpdateDTO;
import hexlet.code.repository.TaskRepository;
import hexlet.code.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping(path = "/tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO dto) {
        var result = taskService.getAll(dto);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDTO> show(@PathVariable Long id) {
        var result = taskService.findById(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskCreateDTO data) {
        var result = taskService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO data) {
        System.out.println("update start");
        System.out.println();
        var result = taskService.update(id, data);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(path = "/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
