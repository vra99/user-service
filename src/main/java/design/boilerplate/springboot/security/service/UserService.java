package design.boilerplate.springboot.security.service;

import design.boilerplate.springboot.model.User;
import design.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.security.dto.RegistrationRequest;
import design.boilerplate.springboot.security.dto.RegistrationResponse;

import java.util.List;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
public interface UserService {

	User findByUsername(String username);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

	List<User> fetchUsersList();

	User addAddress(String username, String address);

	void deleteUser(String username);

	User updateUser(User user, Long userId);

	User updatePassword(User user, Long userId);
}
