package hexlet.code.service;

import hexlet.code.DTO.taskStatusesDTO.TaskStatusCreateDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusDTO;
import hexlet.code.DTO.taskStatusesDTO.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusMapper taskStatusMapper;

    public List<TaskStatusDTO> getAll() {
        var tsStatuses = taskStatusRepository.findAll();
        return tsStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
    }

    public TaskStatusDTO findById(Long id) {
        var tsStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id " + id + " not found"));
        return taskStatusMapper.map(tsStatus);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO data) {
        var tsStatus = taskStatusMapper.map(data);
        taskStatusRepository.save(tsStatus);
        return taskStatusMapper.map(tsStatus);
    }

    public TaskStatusDTO update(Long id, TaskStatusUpdateDTO data) {
        var tsStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id " + id + " not found"));
        taskStatusMapper.update(data, tsStatus);
        taskStatusRepository.save(tsStatus);
        return taskStatusMapper.map(tsStatus);
    }

    public void delete(Long id) {
        taskStatusRepository.deleteById(id);
    }
}
