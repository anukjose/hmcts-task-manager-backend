
package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.hmcts.reform.dev.services.TaskService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the service so controller tests do not access the database.
    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldRejectBlankTaskTitle() throws Exception {
        String requestBody = """
                {
                    "title": " ",
                    "description": "Testing title validation",
                    "status": "TODO",
                    "dueDateTime": "2026-11-05T14:30:00"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title")
                        .value("Task title must not be blank"));
    }

    @Test
    void shouldRejectMissingTaskStatus() throws Exception {
        String requestBody = """
                {
                    "title": "Missing status test",
                    "description": "Testing required status"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value("Task status must be provided"));
    }

    @Test
    void shouldRejectInvalidTaskStatus() throws Exception {
        String requestBody = """
                {
                    "title": "Invalid status test",
                    "description": "Testing status validation",
                    "status": "NOT_STARTED"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Invalid request body. Check the JSON format and supplied values."));
    }

    @Test
    void shouldRejectTaskIdZero() throws Exception {
        mockMvc.perform(get("/api/tasks/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectNegativeTaskId() throws Exception {
        mockMvc.perform(get("/api/tasks/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMissingStatusWhenUpdatingTask() throws Exception {
        mockMvc.perform(patch("/api/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
