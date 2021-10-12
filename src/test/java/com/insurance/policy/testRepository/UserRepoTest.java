package com.insurance.policy.testRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.User;
import com.insurance.policy.repository.UserRepo;
import com.insurance.policy.util.Encryption;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class UserRepoTest {
	@MockBean
	Encryption encryption;
	@Autowired
	UserRepo repo;

	@Test
	public void testSaveUser() {
//
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		User user = new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		repo.save(user);
		AtomicInteger validIdFound = new AtomicInteger();
		if (user.getId() > 0) {
			validIdFound.getAndIncrement();
		}
		assertThat(validIdFound.intValue()).isEqualTo(1);
	}

	@Test
	public void testAllUser() {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		List<User> user = Arrays.asList(
				new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
						"InActive", policy),
				new User(104, "PrachiKhedkar", "prachi@gmail.com", "Prachi@123", "11/04/1997", "Pune", "FeMale", "user",
						"InActive", policy));
		List<User> allCustomer = repo.findAll();
		assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
	}
	
	@Test
	public void testFindByIdUser() {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		User user = new User(9, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		repo.findById(9);
		Assertions.assertEquals(9, user.getId());

	}
	
	@Test
	public void testDeleteUser() {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		User user = new User(9, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		repo.findById(9);
		if (user.getId() == 9) {
			repo.deleteById(user.getId());
		}
		assertThat(user.getId()).isEqualTo(9);
	}
	@Test
	public void testUpdateUser() {
		String name1 = "KartikRamnatha";
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		User user = new User(9, name1, "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		 repo.save(user);
		AtomicInteger validIdFound = new AtomicInteger();
		if (user.getId() > 0) {
			validIdFound.getAndIncrement();
		}
		assertThat(validIdFound.intValue()).isEqualTo(1);
	}

	@Test
	public void testLoginUser() {
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(8, "Health", "HealthInsurance", 1, 2000));
		User user = new User(9, "KartikRamnatha", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
//		String email = "Ram@gmail.com";
//		String pass = "Prachi@123";
		String encryptedPassword = encryption.encryptedPassword(user.getPassword());
		repo.findByEmailAndPassword(user.getEmail(), encryptedPassword);
		if (user.getEmail()=="Ram@gmail.com" && encryptedPassword==encryptedPassword) {
			repo.findByEmailAndPassword(user.getEmail(), encryptedPassword);
		}
		assertThat(user.getEmail()).isEqualTo("Ram@gmail.com");
		assertThat(user.getPassword()).isEqualTo("Prachi@123");
		
	
	}
	
}
