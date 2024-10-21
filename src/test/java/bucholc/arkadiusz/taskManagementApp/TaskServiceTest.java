package bucholc.arkadiusz.taskManagementApp;


import bucholc.arkadiusz.taskManagementApp.exception.TaskNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.repository.TaskRepository;
import bucholc.arkadiusz.taskManagementApp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskService taskService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUpdateTask_TaskNotFound() {
	
		Long taskId = 1L;
		when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

	
		assertThrows(TaskNotFoundException.class, () -> {
			taskService.updateTask(taskId, Map.of("title", "New Task"));
		});
	}

	@Test
	void testUpdateTaskStatus() {
	
		Long taskId = 1L;
		Task task = new Task();
		task.setId(taskId);
		task.setStatus(TaskStatus.PENDING);

		when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
		when(taskRepository.save(task)).thenReturn(task);

		
		Task updatedTask = taskService.updateTask(taskId, Map.of("status", "IN_PROGRESS"));


		assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
		verify(taskRepository).save(task);
	}


}
