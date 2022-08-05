package design.boilerplate.springboot.security.service;

import design.boilerplate.springboot.model.User;
import design.boilerplate.springboot.model.UserRole;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.security.dto.AuthenticatedUserDto;
import design.boilerplate.springboot.security.dto.RegistrationRequest;
import design.boilerplate.springboot.security.dto.RegistrationResponse;
import design.boilerplate.springboot.security.mapper.UserMapper;
import design.boilerplate.springboot.service.UserValidationService;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserValidationService userValidationService;

	private final GeneralMessageAccessor generalMessageAccessor;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override public List<User> fetchUsersList()
	{
		return (List<User>)
				userRepository.findAll();
	}

	public User addAddress(String username, String address)
	{
		User user = userRepository.findByUsername(username);
		user.setAddress(address);
		userRepository.save(user);
		return user;
	}

	@Override
	public void deleteUser(String username)
	{
		User user = userRepository.findByUsername(username);
		userRepository.delete(user);
	}

	public User
	updateUser(User user,
					 Long userId)
	{
		User userDB
				= userRepository.findById(userId)
				.get();

		if (Objects.nonNull(user.getAddress())
				&& !"".equalsIgnoreCase(
				user.getAddress())) {
			userDB.setAddress(
					user.getAddress());
		}

		if (Objects.nonNull(
				user.getName())
				&& !"".equalsIgnoreCase(
				user.getName())) {
			userDB.setName(
					user.getName());
		}

		if (Objects.nonNull(
				user.getUsername())
				&& !"".equalsIgnoreCase(
				user.getUsername())) {
			userDB.setUsername(
					user.getUsername());
		}

		if (Objects.nonNull(
				user.getEmail())
				&& !"".equalsIgnoreCase(
				user.getEmail())) {
			userDB.setEmail(
					user.getEmail());
		}

		return userRepository.save(userDB);
	}

	public User updatePassword(User user, Long userId)
	{
		User userDB
				= userRepository.findById(userId)
				.get();

		userDB.setPassword(
				bCryptPasswordEncoder.encode(
						user.getPassword()));

		return userRepository.save(userDB);
	}


	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(UserRole.USER);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		log.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}
}
