package hexlet.code.DTO.TasksDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class TaskDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("index")
    private int index;

    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @JsonProperty("assignee_id")
    private Long assigneeId;
    @JsonProperty("title")
    private String name;
    @JsonProperty("content")
    private String description;
    @JsonProperty("status")
    private String status;
    @JsonProperty("taskLabelIds")
    private JsonNullable<Set<Long>> labelsId;
}
