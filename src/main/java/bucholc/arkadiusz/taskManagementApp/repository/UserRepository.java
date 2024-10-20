package bucholc.arkadiusz.taskManagementApp.repository;

import bucholc.arkadiusz.taskManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
