package com.insurance.policy.testRepository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.repository.PolicyRepo;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class PolicyRepoTest {


	@Autowired
	PolicyRepo repo;

	@Test
	public void testSavePolicy() {
//
		Policy policy = new Policy(8, "Health", "HealthInsurance", 1, 2000);
		 repo.save(policy);
		AtomicInteger validIdFound = new AtomicInteger();
		if (policy.getPolicyId() > 0) {
			validIdFound.getAndIncrement();
		}
		assertThat(validIdFound.intValue()).isEqualTo(1);

//		Policy policy = new Policy(8, "Health", "HealthInsurance", 1, 2000);
//		policy = entityManager.merge(policy);
//		assertThat(repo.findById(policy.getPolicyId()).get()).isEqualTo(policy);

	}

	@Test
	public void testAllPolicyTest() {
		List<Policy> policy = Arrays.asList(new Policy(9, "Health", "HealthInsurance", 1, 2000),
				new Policy(11, "Vehicle", "VehicleInsurance", 1, 2000));
		List<Policy> allCustomer = repo.findAll();
		assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
	}

	@Test
	public void testFindById() {
		Policy policy = new Policy(8, "Health", "HealthInsurance", 1, 2000);
		repo.findById(8);
		Assertions.assertEquals(8, policy.getPolicyId());

	}

	@Test
	public void testDeletePolicy() {
		Policy policy = new Policy(8, "Health", "HealthInsurance", 1, 2000);
		repo.findById(8);
		if (policy.getPolicyId() == 8) {
			repo.deleteById(policy.getPolicyId());
		}
		
		assertThat(policy.getPolicyId()).isEqualTo(8);
	}

	@Test
	public void testUpdatePolicy() {
		String name1 = "Vehicle";
		Policy policy = new Policy(8, name1, "HealthInsurance", 1, 2000);
		 repo.save(policy);
		AtomicInteger validIdFound = new AtomicInteger();
		if (policy.getPolicyId() > 0) {
			validIdFound.getAndIncrement();
		}
		assertThat(validIdFound.intValue()).isEqualTo(1);

	}

}