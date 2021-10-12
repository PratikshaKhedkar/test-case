package com.insurance.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.policy.entity.Policy;

@Repository
public interface PolicyRepo extends JpaRepository<Policy, Integer> {

	Policy findByPolicyId(int id);


}
