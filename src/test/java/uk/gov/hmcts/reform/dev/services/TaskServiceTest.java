
package uk.gov.hmcts.reform.dev.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.hmcts.reform.dev.dto.CreateTaskRequest;
import uk.gov.hmcts.reform.dev.dto.UpdateTaskStatusRequest;
import uk.gov.hmcts.reform.dev.exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(
                "Prepare coding challenge",
                "Implement task management API",
                TaskStatus.TODO,
                LocalDateTime.of(2026, 11, 1, 17, 0)
        );
    }

    @Test
    void shouldCreateTask() {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Prepare coding challenge");
        request.setDescription("Implement task management API");
        request.setStatus(TaskStatus.TODO);
        request.setDueDateTime(LocalDateTime.of(2026, 11, 1, 17, 0));

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(request);

        assertEquals("Prepare coding challenge", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Prepare coding challenge", result.get(0).getTitle());
    }

    @Test
    void shouldReturnTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals("Prepare coding challenge", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotExist() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(99L)
        );
    }

    @Test
    void shouldUpdateTaskStatus() {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus(TaskStatus.COMPLETED);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        Task result = taskService.updateTaskStatus(1L, request);

        assertEquals(TaskStatus.COMPLETED, result.getStatus());
        verify(taskRepository).save(task);
    }

    @Test
    void shouldDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).delete(task);
    }
}
