package bucholc.arkadiusz.taskManagementApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;

	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private String email;
}
