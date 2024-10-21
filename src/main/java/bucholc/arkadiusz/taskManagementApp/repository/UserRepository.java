package bucholc.arkadiusz.taskManagementApp.repository;

import bucholc.arkadiusz.taskManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
