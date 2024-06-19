package hexlet.code.controller.api;

import hexlet.code.DTO.taskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/api/task_statuses")
public class TaskController {
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private TaskStatusMapper taskStatusMapper;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> index() {
        var tsStatuses = taskStatusRepository.findAll();
        var result = tsStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> show(@PathVariable Long id) {
        var tsStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "TaskStatus with id " + id + " not found"));
        var result = taskStatusMapper.map(tsStatus);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(path = "")
    public ResponseEntity<TaskStatusDTO> create(@Valid @RequestBody TaskStatusCreateDTO data) {
        var tsStatus = taskStatusMapper.map(data);
        taskStatusRepository.save(tsStatus);
        var result = taskStatusMapper.map(tsStatus);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskStatusDTO> update(@PathVariable Long id, @Valid @RequestBody TaskStatusUpdateDTO data) {
        var tsStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "TaskStatus with id " + id + " not found"));
        taskStatusMapper.update(data, tsStatus);
        var result = taskStatusMapper.map(tsStatus);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        taskStatusRepository.deleteById(id);
    }
}
