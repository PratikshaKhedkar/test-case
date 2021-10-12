package com.insurance.policy.testController;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.entity.User;
import com.insurance.policy.service.UserService;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

	@MockBean
	UserService userService;
	private User mockUser;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void initEmployeeObject() {
		mockUser = new User();
		mockUser.setId(101);
		mockUser.setName("RamShrinivas");
		mockUser.setEmail("Ram@gmail.com");
		mockUser.setPassword("Prachi@123");
		mockUser.setDateOfBirth("19/04/1998");
		mockUser.setCity("Pune");
		mockUser.setGender("Male");
		mockUser.setRole("user");
		mockUser.setPolicyStatus("InActive");
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		mockUser.setPolicy(policy);
	}

	@Test
	@DisplayName("POST /create")
	public void testCreateUser() throws Exception {
		// User mockUser = new User();
//		mockUser.setId(101);
//		mockUser.setName("RamShrinivas");
//		mockUser.setEmail("Ram@gmail.com");
//		mockUser.setPassword("Prachi@123");
//		mockUser.setDateOfBirth("19/04/1998");
//		mockUser.setCity("Pune");
//		mockUser.setGender("Male");
//		mockUser.setRole("user");
//		mockUser.setPolicyStatus("InActive");
//		List<Policy> policy = new ArrayList<>();
//		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
//		mockUser.setPolicy(policy);
		String inputJson = this.mapToJson(mockUser);
		Mockito.when(userService.CreateUser(Mockito.any(User.class))).thenReturn(mockUser);
		String url = "/createUser";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputJson = response.getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
// TODO Auto-generated method stub
		return objectMapper.writeValueAsString(object);
	}

	@DisplayName("GET /getAllUser")
	@Test
	public void testListUser() throws Exception {
		List<User> listUser = new ArrayList<>();
		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		listUser.add(new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy));
		listUser.add(new User(102, "ShamShrinivas", "sham@gmail.com", "Prachi@123", "19/05/1996", "Pune", "Male",
				"user", "InActive", policy));
		Mockito.when(userService.getUser()).thenReturn(listUser);
		String url = "/getAllUser";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	@DisplayName("GET/getUserById")
	@Test
	public void testUserById() throws Exception {
//		User mockUser = new User();
//		mockUser.setId(101);
//		mockUser.setName("RamShrinivas");
//		mockUser.setEmail("Ram@gmail.com");
//		mockUser.setPassword("Prachi@123");
//		mockUser.setDateOfBirth("19/04/1998");
//		mockUser.setCity("Pune");
//		mockUser.setGender("Male");
//		mockUser.setRole("user");
//		mockUser.setPolicyStatus("InActive");
//		List<Policy> policy = new ArrayList<>();
//		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
//		mockUser.setPolicy(policy);
		Mockito.when(userService.getUserById(101)).thenReturn(Optional.of(mockUser));
		String url = "/getUser/101";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String inputJson = this.mapToJson(mockUser);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
// mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@DisplayName("GET/getUserbymail")
	@Test
	public void testUserByEmail() throws Exception {
//		User mockUser = new User();
//		mockUser.setId(101);
//		mockUser.setName("RamShrinivas");
//		mockUser.setEmail("Ram@gmail.com");
//		mockUser.setPassword("Prachi@123");
//		mockUser.setDateOfBirth("19/04/1998");
//		mockUser.setCity("Pune");
//		mockUser.setGender("Male");
//		mockUser.setRole("user");
//		mockUser.setPolicyStatus("InActive");
//		List<Policy> policy = new ArrayList<>();
//		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
//		mockUser.setPolicy(policy);
		Mockito.when(userService.viewUserbyEmail("Ram@gmail.com")).thenReturn(Optional.of(mockUser));
		String url = "/viewUserbyEmail/Ram@gmail.com";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String inputJson = this.mapToJson(mockUser);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
// mockMvc.perform(get(url)).andExpect(status().isOk());

	}

	@Test
	public void testDeleteUser() throws Exception {
//		User user = new User();
//		user.setId(101);
//		user.setName("RamShrinivas");
//		user.setEmail("Ram@gmail.com");
//		user.setPassword("Prachi@123");
//		user.setDateOfBirth("19/04/1998");
//		user.setCity("Pune");
//		user.setGender("Male");
//		user.setRole("user");
//		user.setPolicyStatus("InActive");
//		List<Policy> policy = new ArrayList<>();
//		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
//		user.setPolicy(policy);
		Mockito.when(userService.deleteUser(mockUser.getId())).thenReturn(Optional.of(mockUser));
		mockMvc.perform(delete("/deleteUser/" + mockUser.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUser() throws Exception {

		List<Policy> policy = new ArrayList<>();
		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
		User user = new User(101, "RamShrinivas", "Ram@gmail.com", "Prachi@123", "19/04/1998", "Pune", "Male", "user",
				"InActive", policy);
		user.setName("PratikshaKhedkar");
		String inputJson = this.mapToJson(user);
		Mockito.when(userService.updateUser(101, user)).thenReturn(user);
		MvcResult mvcResult = mockMvc
				.perform(put("/updateuser/{id}", 101).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200, status);
// User updateUser=(userService.updateUser(user.getId(),user));
// Assertions.assertEquals(updateUser,user);
//

	}

	@Test
	public void testLoginUser() throws Exception {
//		User mockUser = new User();
//		mockUser.setId(101);
//		mockUser.setName("RamShrinivas");
//		mockUser.setEmail("Ram@gmail.com");
//		mockUser.setPassword("Prachi@123");
//		mockUser.setDateOfBirth("19/04/1998");
//		mockUser.setCity("Pune");
//		mockUser.setGender("Male");
//		mockUser.setRole("user");
//		mockUser.setPolicyStatus("InActive");
//		List<Policy> policy = new ArrayList<>();
//		policy.add(new Policy(501, "Health", "HealthInsurance", 1, 8000));
//		mockUser.setPolicy(policy);
		String inputJson = this.mapToJson(mockUser);
		Mockito.when(userService.userLogin(Mockito.any(User.class))).thenReturn(Optional.of(mockUser));
		String url = "/login";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputJson = response.getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
}
