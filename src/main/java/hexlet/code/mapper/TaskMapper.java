package hexlet.code.mapper;

import hexlet.code.DTO.TasksDTO.TaskCreateDTO;
import hexlet.code.DTO.TasksDTO.TaskDTO;
import hexlet.code.DTO.TasksDTO.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "status", source = "taskStatus", qualifiedByName = "statusSlug")
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreateDTO dto);
    @Mapping(target = "status", source = "status.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "labelsId", source = "labels", qualifiedByName = "modelToDto")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "dtoToModel")
    @Mapping(target = "status", source = "taskStatus", qualifiedByName = "statusSlug")
    @Mapping(target = "assignee", source = "assigneeId", qualifiedByName = "userNull")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Named("modelToDto")
    public Set<Long> modelToLabelIds(Set<Label> model) {
        /*if (model == null || model.isEmpty()) {
            throw new ResourceNotFoundException("Labels is null or empty!");
        }*/
        var collectLabel =  model.stream()
                .map(v -> v.getId())
                .collect(Collectors.toSet());
        return collectLabel;
    }
    @Named("dtoToModel")
    public Set<Label> dtoToModel(Set<Long> model) {
        return new HashSet<>(labelRepository.findByIdIn(model));
    }
    @Named("statusSlug")
    public TaskStatus statusSlugToModel(String slug) {
        return taskStatusRepository.findBySlug(slug)
                .orElseThrow();
    }

    @Named("userNull")
    public User userNull(Long id) {
        if (id == null) {
            return null;
        } else {
            return userRepository.findById(id).orElse(null);
        }
    }
}
