package bucholc.arkadiusz.taskManagementApp;

import bucholc.arkadiusz.taskManagementApp.exception.UserNotFoundException;
import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.repository.UserRepository;
import bucholc.arkadiusz.taskManagementApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testUpdateUser_UserNotFound() {

		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			userService.updateUser(userId, Map.of("firstName", "John"));
		});
	}

	@Test
	void testUpdateUserFirstName() {

		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		user.setFirstName("OldName");

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		User updatedUser = userService.updateUser(userId, Map.of("firstName", "NewName"));

		assertEquals("NewName", updatedUser.getFirstName());
		verify(userRepository).save(user);
	}

}
