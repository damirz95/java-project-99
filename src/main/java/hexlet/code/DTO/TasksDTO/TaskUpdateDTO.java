package hexlet.code.DTO.TasksDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Set;

@Getter
@Setter
@ToString
public class TaskUpdateDTO {
    @NotBlank
    @JsonProperty("title")
    private JsonNullable<String> name;

    @JsonProperty("content")
    private JsonNullable<String> description;

    @NotBlank
    @JsonProperty("status")
    private JsonNullable<String> taskStatus;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;

    private JsonNullable<Set<Long>> taskLabelIds;
}
