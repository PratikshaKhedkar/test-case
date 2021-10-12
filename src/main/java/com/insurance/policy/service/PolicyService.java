package com.insurance.policy.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insurance.policy.controller.PolicyController;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.PolicyRepo;

@Service
@Transactional
public class PolicyService {

	@Autowired
	private PolicyRepo policyRepo;
	


	Logger logger = LoggerFactory.getLogger(PolicyController.class);

	// This method is used for creating policy
	public Policy policyCreation(Policy policy) {
		logger.info("Policy created Succesfully " +	policyRepo.save(policy));
		policyRepo.save(policy);
		return policy;
	}

	// This method is used to fetch all the policy
	public List<Policy> getPolicy() {
		logger.info("List of Policy Retrive Succesfully "+ policyRepo.findAll());
		return policyRepo.findAll();
	}

	// This method is used to fetch user details by using user id.
	public Policy findById(int id) {
		logger.info("Policy Retrive Succesfully By id ");
		Policy policyList = policyRepo.findByPolicyId(id);
		logger.info(""+policyList);
		return policyList;
	}
	// This method is used for updating the policy details

	public Policy UpdatePolicy(int policyId, Policy policyDetails) throws Exception {
		Policy policy = policyRepo.findById(policyId)
				.orElseThrow(() -> new ResourceNotFoundException("policy not found for this id :: " + policyId));
		policy.setPolicyName(policyDetails.getPolicyName());
		policy.setDescription(policyDetails.getDescription());
		policy.setDuration(policyDetails.getDuration());
		policy.setPrice(policyDetails.getPrice());
		logger.info("Policy updated Succesfully "+policyRepo.save(policy));
		return policyRepo.save(policy);

	}

	// delete Policy
	@Transactional
	public Optional<Policy> deletePolicy(int id) throws ResourceNotFoundException {
		Optional<Policy> policy = policyRepo.findById(id);
		if (policy.isPresent()) {
			
			logger.info("deleted Policy Succesfully by id "+id);
			policyRepo.deleteById(id);

		} else {
			throw new ResourceNotFoundException("Policy Not Found with id " + id);
		}

		return policy;
	}

// This method is used for purchasing policies
//	public Policy buyPolicy(Policy purchasePolicy) throws ResourceNotFoundException {
//		int policy_Id = purchasePolicy.getPolicyId();
//		Optional<Policy> user = policyRepo.findById(policy_Id);
//		if (user != null) {
//			purchasePolicy.getPolicyName();
//			purchasePolicy.getDescription();
//			purchasePolicy.getDuration();
//			purchasePolicy.getPrice();
//			policyRepo.save(purchasePolicy);
//		}
//		return purchasePolicy;
//}
}