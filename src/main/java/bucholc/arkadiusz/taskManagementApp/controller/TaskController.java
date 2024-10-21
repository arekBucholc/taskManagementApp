package bucholc.arkadiusz.taskManagementApp.controller;

import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public List<Task> getAllTasks() {
		return taskService.findAll();
	}
	
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskService.saveTask(task);
	}
	
	@PutMapping("/id")
	public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
		Task task = taskService.findTaskById(id);
		if (task != null) {
			task.setTitle(updatedTask.getTitle());
			task.setDescription(updatedTask.getDescription());
			task.setStatus(updatedTask.getStatus());
			task.setDueDate(updatedTask.getDueDate());
			return taskService.saveTask(task);
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
	}
	
	@GetMapping("/status/{status}")
	public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
		return taskService.findTaskByStatus(status);
	}
	
	@PatchMapping("/{id}")
	public Task partiallyUpdateTask(@PathVariable Long id, @RequestBody Map<String, Object> updatesMap) {
		return taskService.partialUpdate(id, updatesMap);
	}
}
