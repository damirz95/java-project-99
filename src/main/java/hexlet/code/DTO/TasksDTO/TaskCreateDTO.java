package hexlet.code.DTO.TasksDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {
    @JsonProperty("index")
    private JsonNullable<Integer> index;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @NotBlank
    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private JsonNullable<String> description;

    @JsonProperty("status")
    private String taskStatus;

    @JsonProperty("taskLabelIds")
    private Set<Long> taskLabelIds;
}
