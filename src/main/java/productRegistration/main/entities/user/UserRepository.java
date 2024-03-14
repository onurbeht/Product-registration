package productRegistration.main.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    //Created a new method to find user by login
    UserDetails findByLogin(String login);
}
