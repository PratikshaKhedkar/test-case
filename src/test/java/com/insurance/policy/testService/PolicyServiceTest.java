package com.insurance.policy.testService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.insurance.policy.entity.Policy;
import com.insurance.policy.exception.ResourceNotFoundException;
import com.insurance.policy.repository.PolicyRepo;
import com.insurance.policy.service.PolicyService;

@SpringBootTest

public class PolicyServiceTest {
@MockBean
PolicyRepo repo;

 @Autowired
PolicyService service;

 @Test
@DisplayName("Test Get all Policy")
public void getAllPolicy() {
when(repo.findAll())
.thenReturn(Stream.of(new Policy(501, "Health", "Healthinsu", 1, 52000)).collect(Collectors.toList()));
Assertions.assertEquals(1, service.getPolicy().size());

 }

 @Test
@DisplayName("Test save Policy")
public void CreatePolicyTest() throws ResourceNotFoundException {
Policy policy = new Policy(501, "Health", "HealthInsurance", 1, 8000);
when(repo.save(policy)).thenReturn(policy);
Assertions.assertEquals(policy, service.policyCreation(policy));
}

 @Test
@DisplayName("Test delete Policy")
public void deletePolicy() throws ResourceNotFoundException {
Policy policy = new Policy(501, "Health", "HealthInsurance", 1, 8000);
doReturn(Optional.of(policy)).when(repo).findById(501);
service.deletePolicy(policy.getPolicyId());
verify(repo, times(1)).deleteById(policy.getPolicyId());
}

 @Test
@DisplayName("Test getPolicyById Policy")
public void getPolicyById() {

 when(repo.findByPolicyId(501)).thenReturn(new Policy(501, "Health", "HealthInsurance", 1, 8000));
 Policy poli = service.findById(501);
Assertions.assertEquals("Health", poli.getPolicyName());
Assertions.assertEquals("HealthInsurance", poli.getDescription());
Assertions.assertEquals(1, poli.getDuration());
Assertions.assertEquals(8000, poli.getPrice());


}


@Test
@DisplayName("Test UpdatePolicy Policy")
public void testUpdatePolicy() throws Exception{
String name1 = "Vehicle";
Policy policy = new Policy(501, name1, "HealthInsurance", 1, 8000);
doReturn(Optional.of(policy)).when(repo).findById(501);
when(repo.save(policy)).thenReturn(policy);
Assertions.assertEquals(policy, service.UpdatePolicy(policy.getPolicyId(), policy));
}

}