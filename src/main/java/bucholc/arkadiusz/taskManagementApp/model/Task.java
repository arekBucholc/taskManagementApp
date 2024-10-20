package bucholc.arkadiusz.taskManagementApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private String status;
	private LocalDate dueDate;
	
	@ManyToMany
	@JoinTable(
			name = "task_users",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> assignedUsers = new HashSet<>();
}
