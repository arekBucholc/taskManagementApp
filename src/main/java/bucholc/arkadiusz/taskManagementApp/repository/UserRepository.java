package bucholc.arkadiusz.taskManagementApp.repository;

import bucholc.arkadiusz.taskManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	List<User> findByLastName(String lastName);
}
