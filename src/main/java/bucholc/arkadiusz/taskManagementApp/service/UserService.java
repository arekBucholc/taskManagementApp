package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void removeUser(Long id) {
		userRepository.deleteById(id);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User partialUpdate(Long id, Map<String, Object> updatesMap) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
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
			}
		});
		return userRepository.save(user);
	}
}
