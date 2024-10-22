package bucholc.arkadiusz.taskManagementApp;

import bucholc.arkadiusz.taskManagementApp.controller.TaskController;
import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
 class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@org.springframework.boot.test.mock.mockito.MockBean
	private TaskService taskService;

	@Autowired
	private ObjectMapper objectMapper;

	private Task task;

	@BeforeEach
	 void setup() {
		task = new Task();
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setStatus(TaskStatus.TO_DO);
		task.setDueDate(LocalDate.now().plusDays(5));
	}

	@Test
	 void testGetAllTasks() throws Exception {
		when(taskService.findAll()).thenReturn(Collections.singletonList(task));

		mockMvc.perform(get("/tasks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Task"));
	}

	@Test
	public void testGetTasksByStatus() throws Exception {
		List<Task> tasks = Collections.singletonList(task);
		when(taskService.findTaskByStatus(TaskStatus.TO_DO)).thenReturn(tasks);

		mockMvc.perform(get("/tasks/status/{status}", TaskStatus.TO_DO))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Task"))
				.andExpect(jsonPath("$[0].status").value("TO_DO"));
	}

	@Test
	 void testCreateTask() throws Exception {
		when(taskService.createTask(any(Task.class), any())).thenReturn(task);

		mockMvc.perform(post("/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(task)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Test Task"));
	}

	@Test
	 void testDeleteTask_Success() throws Exception {
		mockMvc.perform(delete("/tasks/{id}", 1L))
				.andExpect(status().isNoContent());
	}
	

	@Test
	 void testUpdateTask() throws Exception {
		when(taskService.updateTask(anyLong(), any())).thenReturn(task);

		mockMvc.perform(patch("/tasks/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(task)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Test Task"));
	}

	@Test
	public void testGetTasksByUserLastName() throws Exception {
		List<Task> tasks = Collections.singletonList(task);
		when(taskService.getTasksByUserLastName("Kowalski")).thenReturn(tasks);

		mockMvc.perform(get("/tasks/user/lastname/{lastName}", "Kowalski"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Test Task"));
	}
}
