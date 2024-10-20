package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.exception.UserNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

	public Task partialUpdate(Long id, Map<String, Object> updates) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		updates.forEach((key, value) -> {
			switch (key) {
			case "title":
				task.setTitle((String) value);
				break;
			case "description":
				task.setDescription((String) value);
				break;
			case "status":
				task.setStatus(String.valueOf(TaskStatus.valueOf((String) value)));
				break;
			case "dueDate":
				task.setDueDate(LocalDate.parse((String) value));
				break;
			}
		});

		return taskRepository.save(task);
	}
}
