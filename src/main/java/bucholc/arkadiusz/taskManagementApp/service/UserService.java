package bucholc.arkadiusz.taskManagementApp.service;

import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
