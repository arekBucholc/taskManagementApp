package bucholc.arkadiusz.taskManagementApp.repository;

import bucholc.arkadiusz.taskManagementApp.model.Task;
import bucholc.arkadiusz.taskManagementApp.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByStatus(TaskStatus status);
	
	boolean existsByAssignedUsersId(Long id);

	@Query("SELECT t FROM Tasks t JOIN t.assignedUsers u WHERE u.lastName = :lastName")
	List<Task> findByUserLastName(String lastName);

	@Query("SELECT t FROM Tasks t JOIN t.assignedUsers u WHERE u.email = :email")
	List<Task> findByUserEmail(String email);
}
