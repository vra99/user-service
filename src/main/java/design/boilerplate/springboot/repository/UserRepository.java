package design.boilerplate.springboot.repository;

import design.boilerplate.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author Victor
 */

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(
			String username
	);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
