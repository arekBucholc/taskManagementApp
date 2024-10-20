package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

	public Task findTaskById(Long id) {
		return taskRepository.findById(id).orElse(null);
	}

	public List<Task> findTaskByStatus(TaskStatus status) {
		return taskRepository.findByStatus(status);
	}
}
