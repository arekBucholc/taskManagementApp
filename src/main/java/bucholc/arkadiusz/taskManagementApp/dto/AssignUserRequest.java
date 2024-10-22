package bucholc.arkadiusz.taskManagementApp.dto;

import lombok.Data;

@Data
public class AssignUserRequest {
	private Long taskId;
	private Long userId;
}
