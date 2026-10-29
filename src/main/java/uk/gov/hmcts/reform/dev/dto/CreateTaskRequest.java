
package uk.gov.hmcts.reform.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CreateTaskRequest {

    @NotBlank(message = "Task title must not be blank")
    @Size(max = 200, message = "Task title must not exceed 200 characters")
    private String title;

    @Size(max = 5000, message = "Task description must not exceed 5000 characters")
    private String description;

    @NotNull(message = "Task status must be provided")
    private TaskStatus status;

    @NotNull(message = "Due date and time is required")
    private LocalDateTime dueDateTime;
}
