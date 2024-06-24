package hexlet.code.specification;

import hexlet.code.DTO.TasksDTO.TaskParamsDTO;
import hexlet.code.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {
    public Specification build(TaskParamsDTO dto) {
        return withTitleCount(dto.getTitleCont())
                .and(withAssigneeId(dto.getAssigneeId()));
    }

    public Specification<Task> withTitleCount(String titleCount) {
        return ((root, query, cb) -> titleCount == null
                ? cb.conjunction()
                : cb.like(root.get("name"), titleCount));
    }
    public Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null
                ? cb.conjunction()
                : cb.equal(root.get("assigne").get("id"), assigneeId);
    }
    private Specification<Task> withStatus(String status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get("taskStatus").get("slug"), status);
    }
    private Specification<Task> withLabelId(Long labelId) {
        return (root, query, cb) -> labelId == null
                ? cb.conjunction()
                : cb.equal(root.get("labels").get("id"), labelId);
    }
}
