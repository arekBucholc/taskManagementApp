package bucholc.arkadiusz.taskManagementApp.controller;

import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.removeUser(id);
	}

	@PatchMapping("/{id}")
	public User partiallyUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updatesMap) {
		return userService.partialUpdate(id, updatesMap);
	}
}
