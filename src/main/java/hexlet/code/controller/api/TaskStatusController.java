package hexlet.code.controller.api;

import hexlet.code.DTO.taskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.service.TaskStatusService;
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
//TODO Привести к единому стилю
@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class TaskStatusController {
    private final TaskStatusService taskStatusService;

    @GetMapping(path = "/task_statuses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> index() {
        var result = taskStatusService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> show(@PathVariable Long id) {
        var result = taskStatusService.findById(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "/task_statuses")
    public ResponseEntity<TaskStatusDTO> create(@Valid @RequestBody TaskStatusCreateDTO data) {
        var result = taskStatusService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> update(@PathVariable Long id, @Valid @RequestBody TaskStatusUpdateDTO data) {
        var result = taskStatusService.update(id, data);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        taskStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
