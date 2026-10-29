
package uk.gov.hmcts.reform.dev.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import uk.gov.hmcts.reform.dev.models.TaskStatus;

@NoArgsConstructor
@Getter
@Setter
public class UpdateTaskStatusRequest {

    @NotNull(message = "Task status must be provided")
    private TaskStatus status;
}
