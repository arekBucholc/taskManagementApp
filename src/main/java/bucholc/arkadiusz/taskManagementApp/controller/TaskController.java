package bucholc.arkadiusz.taskManagementApp.controller;

import bucholc.arkadiusz.taskManagementApp.TaskDTO;
import bucholc.arkadiusz.taskManagementApp.exception.TaskNotFoundException;
import bucholc.arkadiusz.taskManagementApp.exception.UserNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.findAll();
		return ResponseEntity.ok(tasks);
	}
	
	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
		Task task = new Task();
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
		task.setDueDate(taskDTO.getDueDate());
		
		Task createdTask = taskService.createTask(task, taskDTO.getAssignedUserIds());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
	}
	
	@PatchMapping ("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		Task updatedTask = taskService.updateTask(id, updates);
		return ResponseEntity.ok(updatedTask);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTaskById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
		List<Task> foundTasks = taskService.findTaskByStatus(status);
		return ResponseEntity.ok(foundTasks);
	}

	@GetMapping("/user/lastname/{lastName}")
	public ResponseEntity<List<Task>> getTasksByUserLastName(@PathVariable String lastName) {
		List<Task> tasks = taskService.getTasksByUserLastName(lastName);
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/user/email/{email}")
	public ResponseEntity<List<Task>> getTasksByUserEmail(@PathVariable String email) {
		List<Task> tasks = taskService.getTasksByUserEmail(email);
		return ResponseEntity.ok(tasks);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleNoUsersFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(TaskNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}
}
