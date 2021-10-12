package com.insurance.policy.testService;

//import static org.mockito.Mockito.doReturn;
//
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.ArgumentMatchers.any;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.insurance.policy.entity.Policy;
//import com.insurance.policy.entity.User;
//import com.insurance.policy.exception.ResourceNotFoundException;
//import com.insurance.policy.repository.UserRepo;
//import com.insurance.policy.service.UserService;
//import com.insurance.policy.util.Encryption;
//import org.junit.jupiter.api.Assertions;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.User;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.UserRepo;
import com.insurance.policy.service.UserService;
import com.insurance.policy.util.Encryption;

@SpringBootTest
public class UserServiceTest {
	@MockBean
	UserRepo userRepo;
	@Autowired
	UserService userService;
	private User user;
	@MockBean
	Encryption encryption;

	@BeforeEach
	void initEmployeeObject() {
		user = new User();
		user.setId(101);
		user.setName("RamShrinivas");
		user.setEmail("Ram@gmail.com");
		user.setPassword("Prachi@123");
		user.setDateOfBirth("19/04/1998");
		user.setCity("Pune");
		user.setGender("Male");
		user.setRole("user");
		user.setPolicyStatus("InActive");
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		user.setPolicy(policy);
	}

	@Test
	@DisplayName("Test save User")
	public void testCreateUser() throws ResourceNotFoundException {
		doReturn(user).when(userRepo).save(any(User.class));
		User savedUser = userService.CreateUser(user);
		Assertions.assertNotNull(savedUser);
		Assertions.assertEquals(101, savedUser.getId());
	}

	@Test
	@DisplayName("Test Get All User")
	public void testGetAllUser() {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		when(userRepo.findAll()).thenReturn(Stream.of(new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123",
				"19/04/1998", "Pune", "Male", "user", "InActive", policy)).collect(Collectors.toList()));
		Assertions.assertEquals(1, userService.getUser().size());
	}

	@Test
	@DisplayName("Test Get User By Id")
	public void testGetUserById() {
		doReturn(Optional.of(user)).when(userRepo).findById(101);
		Optional<User> user1 = userService.getUserById(101);
		Assertions.assertTrue(user1.isPresent());
		Assertions.assertSame(user1.get(), user);
	}

	@Test
	@DisplayName("Test Get User By Id Not Found")
	void testGetUserByIdWhenInvalidInputThenUnsuccessful() {
		doReturn(Optional.empty()).when(userRepo).findById(101);
		Optional<User> user1 = userService.getUserById(101);
		Assertions.assertFalse(user1.isPresent());
		Assertions.assertTrue(user1.isEmpty());

	}

	@Test
	@DisplayName("Test by user Email")
	void testViewUserByEmail() throws ResourceNotFoundException {
		String email = "Ram@gmail.com";
		doReturn(Optional.of(user)).when(userRepo).findByEmail(email);
		Optional<User> user1 = userService.viewUserbyEmail(email);
		Assertions.assertTrue(user1.isPresent());
		Assertions.assertSame(user1.get(), user);

	}

	@Test
	@DisplayName("Delete user")
	public void testDeleteUser() throws ResourceNotFoundException {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		User user = new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		doReturn(Optional.of(user)).when(userRepo).findById(101);
		userService.deleteUser(user.getId());
		verify(userRepo, times(1)).deleteById(user.getId());
	}

	@Test
	@DisplayName("update user")
	public void testUserUpdate() throws ResourceNotFoundException {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		String name1 = "PratikshaKhedkar";
		User user = new User(101, name1, "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		doReturn(Optional.of(user)).when(userRepo).findById(101);
		when(userRepo.save(user)).thenReturn(user);
		Assertions.assertEquals(user, userService.updateUser(user.getId(), user));
	}

	@Test
	@DisplayName("Login user")
	public void testLogin() throws ResourceNotFoundException {
		String email = "Ram@gmail.com";
		String pass = "Prachi@123";
		String encryptedPassword = encryption.encryptedPassword(pass);
		doReturn(Optional.of(user)).when(userRepo).findByEmailAndPassword(email, encryptedPassword);
		userService.userLogin(user);
		
		// verify(userRepo,
		// times(1)).findByEmailAndPassword(user.getEmail(),user.getPassword());

	}

}
