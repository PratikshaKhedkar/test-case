package com.insurance.policy.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insurance.policy.controller.UserController;
import com.insurance.policy.entity.User;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.UserRepo;
import com.insurance.policy.util.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class UserService {
	private static final String USER_ROLE = "user";
	private static final String USER_STATUS = "InActive";
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepo userRepo;
	
//	 PasswordEncoder passwordEncoder;
//
//	  public UserService(UserRepo userRepo) {
//	    this.userRepo = userRepo;
//	    this.passwordEncoder = new BCryptPasswordEncoder();
//	  }

	// registration of user

	public User CreateUser(User user) throws ResourceNotFoundException {
//		userRepo.save(user);
//		Optional<User> usr = userRepo.findById(user.getId());
//		if (user != null) {
//			System.out.println("Successfully registered");
//			User u = usr.get();
//			return u;
//
//		} else {
//			throw new ResourceNotFoundException("Registartion Not Done");
//		}
		user.setRole(USER_ROLE);
		user.setPolicyStatus(USER_STATUS);
		user.setPassword(Encryption.encryptedPassword(user.getPassword()));
		logger.info("Created user is "+ userRepo.save(user));
		return userRepo.save(user);
	}

// Get All User List
	public List<User> getUser() {
		logger.info("List of All Users Retreived Sucessfully "+userRepo.findAll());

		return userRepo.findAll();
	}

// get user by id
	public Optional<User> getUserById(int id) {
		logger.info(" Users Retreived Sucessfully By Id " + userRepo.findById(id));
		return userRepo.findById(id);
	}

// delete user

	public Optional<User> deleteUser(int id) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			logger.info(" Users Deleteded Sucessfully By Id "+id);
			userRepo.deleteById(id);

		} else {
			logger.error("User is not Found with id " + id);
		}

		return user;
	}

	// This method is used for updating user details

	public User updateUser(int id, User user) throws ResourceNotFoundException {
		Optional<User> maybeUser = userRepo.findById(id);
		User presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setName(user.getName() != null ? user.getName() : maybeUser.get().getName());
			existingUser.setCity(user.getCity() != null ? user.getCity() : maybeUser.get().getCity());
			existingUser.setGender(user.getGender() != null ? user.getGender() : maybeUser.get().getGender());
			existingUser.setRole(user.getRole() != null ? user.getRole() : maybeUser.get().getRole());
			existingUser.setPolicyStatus(
					user.getPolicyStatus() != null ? user.getPolicyStatus() : maybeUser.get().getPolicyStatus());

			existingUser.setDateOfBirth(
					user.getDateOfBirth() != null ? user.getDateOfBirth() : maybeUser.get().getDateOfBirth());
			logger.info("Successfully updated");
			return existingUser;
		}).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

		return userRepo.save(presentUser);
	}

//This method is used for logging into the user account

	public Optional<User> userLogin(User loginReq) throws ResourceNotFoundException {
		Optional<User> maybeUser = userRepo.findByEmailAndPassword(loginReq.getEmail(),
				Encryption.encryptedPassword(loginReq.getPassword()));

		logger.info(Encryption.encryptedPassword(loginReq.getPassword()));
		logger.info(" " + maybeUser);

		if (maybeUser.isPresent()) {
			// System.out.println("Sucessful login");
			logger.info("Sucessful login");

		} else {
			throw new ResourceNotFoundException("Bad Credentials");
		}
		return maybeUser;

	}

	public Optional<User> viewUserbyEmail(String email) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException("NOT FOUND");
		} else {
			return user;
		}
	}
	
	

}