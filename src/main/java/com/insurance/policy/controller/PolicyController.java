package com.insurance.policy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.policy.entity.Policy;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.service.PolicyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PolicyController {

	@Autowired
	PolicyService policyservice;

	@PostMapping(value = "/addpolicy")
	public Policy createPolicy(@RequestBody Policy policy) {
		policyservice.policyCreation(policy);
		return policy;
	}

	@GetMapping(value = "/getAllPolicy")
	public List<Policy> getAllUsers() {
		return policyservice.getPolicy();

	}

	@GetMapping(value = "/getPolicy/{id}")
	public Policy getPolicyById(@PathVariable int id) {
		return policyservice.findById(id);

	}

	@PutMapping("/updatePolicy/{id}")
	public Policy updatePolicy(@RequestBody Policy policy, @PathVariable int id) throws Exception {
		return policyservice.UpdatePolicy(id, policy);
	}

	@DeleteMapping(value = "/deletePolicy/{id}")
	public String deletePolicy(@PathVariable int id) throws ResourceNotFoundException {
		policyservice.deletePolicy(id)
				.orElseThrow(() -> new ResourceNotFoundException("policy Not Found with id :" + id));
		return "Policy delected.." + id;
	}
	
//	@PostMapping(value = "/buyPolicy")
//	public Policy purchasePolicy(@RequestBody Policy purchasePolicy) throws ResourceNotFoundException {
//		return 	policyservice.buyPolicy( purchasePolicy);
//	
//	}

}
