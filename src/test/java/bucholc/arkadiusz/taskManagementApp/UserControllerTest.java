package bucholc.arkadiusz.taskManagementApp;

import bucholc.arkadiusz.taskManagementApp.controller.UserController;
import bucholc.arkadiusz.taskManagementApp.exception.UserAlreadyExistsException;
import bucholc.arkadiusz.taskManagementApp.exception.UserAssignedToTaskException;
import bucholc.arkadiusz.taskManagementApp.model.User;
import bucholc.arkadiusz.taskManagementApp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private User user;

	@BeforeEach
	 void setup() {
		user = new User();
		user.setId(1L);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john.doe@example.com");
	}

	@Test
	 void testGetAllUsers() throws Exception {
		when(userService.findAll()).thenReturn(Collections.singletonList(user));

		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName").value("John"));
	}

	@Test
	 void testGetUserById() throws Exception {
		when(userService.findById(anyLong())).thenReturn(user);

		mockMvc.perform(get("/users/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	 void testCreateUser() throws Exception {
		when(userService.createUser(any(User.class))).thenReturn(user);

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName").value("John"));
	}

	@Test
	 void testDeleteUser_Success() throws Exception {
		mockMvc.perform(delete("/users/{id}", 1L))
				.andExpect(status().isNoContent());
	}

	@Test
	 void testDeleteUser_UserAssignedToTask() throws Exception {
		doThrow(new UserAssignedToTaskException("User is assigned to task"))
				.when(userService).removeUser(anyLong());

		mockMvc.perform(delete("/users/{id}", 1L))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("User is assigned to task"));
	}

	@Test
	 void testCreateUser_UserAlreadyExists() throws Exception {
		when(userService.createUser(any(User.class))).thenThrow(new UserAlreadyExistsException("User with email john.doe@example.com already exists"));

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("User with email john.doe@example.com already exists"));
	}
}
