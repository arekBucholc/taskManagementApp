package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.exception.UserAlreadyExistsException;
import bucholc.arkadiusz.taskManagementApp.exception.UserAssignedToTaskException;
import bucholc.arkadiusz.taskManagementApp.exception.UserNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.repository.TaskRepository;
import bucholc.arkadiusz.taskManagementApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final TaskRepository taskRepository;

	public UserService(UserRepository userRepository, TaskRepository taskRepository) {
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
		}
		
		return userRepository.save(user);
	}
	
	public void removeUser(Long id) {
		boolean isUserAssignedToTask = taskRepository.existsByAssignedUsersId(id);
		if (isUserAssignedToTask) {
			throw new UserAssignedToTaskException("User with id " + id + " assigned to task, cannot be removed");
		}
		userRepository.deleteById(id);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User updateUser(Long id, Map<String, Object> updatesMap) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		updatesMap.forEach((key, value) -> {
			switch (key) {
				case "firstName":
					user.setFirstName((String) value);
					break;
				case "lastName":
					user.setLastName((String) value);
					break;
				case "email":
					user.setEmail((String) value);
					break;
			default:
				throw new IllegalStateException("Unexpected value: " + key);
			}
		});
		return userRepository.save(user);
	}
}
