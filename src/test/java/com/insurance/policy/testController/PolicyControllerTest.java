package com.insurance.policy.testController;
	
	import static org.assertj.core.api.Assertions.assertThat;

	import static org.mockito.Mockito.when;
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
import com.insurance.policy.service.PolicyService;

	@AutoConfigureMockMvc
	@SpringBootTest
	public class PolicyControllerTest {
	@MockBean
	PolicyService policyService;
	@Autowired
	private MockMvc mockMvc;
	
	private Policy mockPolicy;
	
	@BeforeEach
	void initEmployeeObject() {
		mockPolicy= new Policy();
		mockPolicy.setPolicyId(501);
		mockPolicy.setPolicyName("Health");
		mockPolicy.setDescription("HealthInsurance");
		mockPolicy.setDuration(2);
		mockPolicy.setPrice(4000);
	}
	@Test
	@DisplayName("POST /create")
	public void testCreatePolicy() throws Exception {
//	Policy mockPolicy= new Policy();
//	mockPolicy.setPolicyId(501);
//	mockPolicy.setPolicyName("Health");
//	mockPolicy.setDescription("HealthInsurance");
//	mockPolicy.setDuration(2);
//	mockPolicy.setPrice(4000);
//	
	String inputJson= this.mapToJson(mockPolicy);
	Mockito.when(policyService.policyCreation(Mockito.any(Policy.class))).thenReturn( mockPolicy);
	String url="/addpolicy";
	RequestBuilder requestBuilder=MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON).content(inputJson)
	.contentType(MediaType.APPLICATION_JSON);
	MvcResult result=mockMvc.perform(requestBuilder).andReturn();
	MockHttpServletResponse response=result.getResponse();
	String outputJson= response.getContentAsString();
	assertThat(outputJson).isEqualTo(inputJson);
	Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());

	
	 }
	private String mapToJson(Object object) throws JsonProcessingException {
	ObjectMapper objectMapper=new ObjectMapper();
	// TODO Auto-generated method stub
	return objectMapper.writeValueAsString(object);
	}
	
	@DisplayName("GET /getAllPolicy")
	@Test
	public void testListPolicy() throws Exception {
	List<Policy> listPolicy=new ArrayList<>();
	listPolicy.add(new Policy(501, "Health", "HealthInsurance", 1, 5200));
	listPolicy.add(new Policy(502, "Health", "HealthInsurance", 2, 8000));
	Mockito.when(policyService.getPolicy()).thenReturn(listPolicy);
	String url="/getAllPolicy";
	mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	@DisplayName("GET/getPolicyById")
	@Test
	public void testPolicyById() throws Exception {
//	Policy mockPolicy= new Policy();
//	mockPolicy.setPolicyId(501);
//	mockPolicy.setPolicyName("Health");
//	mockPolicy.setDescription("HealthInsurance");
//	mockPolicy.setDuration(2);
//	mockPolicy.setPrice(4000);
	Mockito.when(policyService.findById(501)).thenReturn(mockPolicy);
	String url="/getPolicy/501";
	RequestBuilder requestBuilder=MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
	MvcResult result=mockMvc.perform(requestBuilder).andReturn();
	String inputJson= this.mapToJson(mockPolicy);
	String outputJson= result.getResponse().getContentAsString();
	assertThat(outputJson).isEqualTo(inputJson);

	}
	@Test
	public void deletePolicy() throws Exception {
	//
//	Policy policy= new Policy();
//	policy.setPolicyId(502);
//	policy.setPolicyName("Health");
//	policy.setDescription("HealthInsurance");
//	policy.setDuration(2);
//	policy.setPrice(4000);
	Mockito.when(policyService.deletePolicy(mockPolicy.getPolicyId())).thenReturn(Optional.of(mockPolicy));
	mockMvc.perform(delete("/deletePolicy/" +mockPolicy.getPolicyId())
	.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
	}

	@Test
	public void updatePolicy() throws Exception {
	Policy policy = new Policy(501, "Health", "HealthInsurance", 1, 8000);
	policy.setPolicyName("Vehicle");
	String inputJson = this.mapToJson(policy);
	when(policyService.UpdatePolicy(501,policy)).thenReturn(policy);
	MvcResult mvcResult = mockMvc.perform(
	put("/updatePolicy/"+ 501)
	.contentType(MediaType.APPLICATION_JSON_VALUE)
	.content(inputJson)).andReturn();
	int status = mvcResult.getResponse().getStatus();
	Assertions.assertEquals(200, status);
	}

	}


