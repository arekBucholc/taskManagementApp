package bucholc.arkadiusz.taskManagementApp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TaskDTO {
	private String title;
	private String description;
	private String status;
	private LocalDate dueDate;
	private Set<Long> assignedUserIds;
}
