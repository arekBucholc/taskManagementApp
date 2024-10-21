package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.exception.TaskNotFoundException;
import bucholc.arkadiusz.taskManagementApp.exception.UserNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.repository.TaskRepository;
import bucholc.arkadiusz.taskManagementApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public Task createTask(Task task, Set<Long> userIds) {
		Set<User> users = new HashSet<>();
		for (Long userId : userIds) {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException(userId));
			users.add(user);
		}
		task.setAssignedUsers(users);
		return taskRepository.save(task);
	}

	public void deleteTaskById(Long id) {
		taskRepository.deleteById(id);
	}

	public List<Task> findTaskByStatus(TaskStatus status) {
		return taskRepository.findByStatus(status);
	}

	public Task updateTask(Long id, Map<String, Object> updates) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

		updates.forEach((key, value) -> {
			switch (key) {
			case "title":
				task.setTitle((String) value);
				break;
			case "description":
				task.setDescription((String) value);
				break;
			case "status":
				task.setStatus(TaskStatus.valueOf((String) value));
				break;
			case "dueDate":
				task.setDueDate(LocalDate.parse((String) value));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + key);
			}
		});

		return taskRepository.save(task);
	}
}
