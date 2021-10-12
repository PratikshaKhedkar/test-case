package com.insurance.policy.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.insurance.policy.entity.User;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	@Autowired
	UserService service;
	
	@PostMapping(value = "/createUser")
	public User addUser(@RequestBody User user) throws ResourceNotFoundException {
		service.CreateUser(user);
		return user;
	}

	@GetMapping(value = "/getAllUser")
	public List<User> getAllUsers() {
		return service.getUser();

	}

	@GetMapping(value = "/getUser/{id}")
	public Optional<User> findUserById(@PathVariable int id) {
		return service.getUserById(id);

	}

	@DeleteMapping(value = "/deleteUser/{id}")
	public String deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		service.deleteUser(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with id :" + id));
		return "User delected.." + id;
	}

	@PutMapping(value = "/updateuser/{id}")
	public User updateUser(@RequestBody User user, @PathVariable int id) throws ResourceNotFoundException {
		return service.updateUser(id, user);

	}

	@PostMapping(value = "/login")
	public Optional<User> userLogin(@RequestBody User loginRequest) throws ResourceNotFoundException {
		return service.userLogin(loginRequest);
	}
	
	@GetMapping("/viewUserbyEmail/{email}")
    public Optional<User> viewUserbyEmail(@PathVariable("email") String email) throws ResourceNotFoundException {
        return service.viewUserbyEmail(email);
    }

	

}
