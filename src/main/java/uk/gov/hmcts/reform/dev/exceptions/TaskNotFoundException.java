
package uk.gov.hmcts.reform.dev.exceptions;

/**
 * Represents an error when a task cannot be found.
 */

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
