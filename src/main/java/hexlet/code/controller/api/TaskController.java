package hexlet.code.controller.api;

import hexlet.code.DTO.TasksDTO.TaskCreateDTO;
import hexlet.code.DTO.TasksDTO.TaskDTO;
import hexlet.code.DTO.TasksDTO.TaskParamsDTO;
import hexlet.code.DTO.TasksDTO.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
@RequestMapping(path = "/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification taskSpecification;

    @GetMapping(path = "")
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO dto) {
        Specification specification = taskSpecification.build(dto);
        List<Task> tasks = taskRepository.findAll(specification);
        List<TaskDTO> result = tasks.stream()
                .map(taskMapper::map)
                .toList();
        System.out.println(result);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> show(@PathVariable Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var result = taskMapper.map(task);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskCreateDTO dto) {
        var task = taskMapper.map(dto);
        taskRepository.save(task);
        var result = taskMapper.map(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO dto) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        taskMapper.update(dto, task);
        taskRepository.save(task);
        var result = taskMapper.map(task);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
