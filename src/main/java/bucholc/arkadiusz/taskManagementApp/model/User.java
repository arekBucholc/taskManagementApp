package bucholc.arkadiusz.taskManagementApp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;

	@Column(unique = true)
	private String email;
}
