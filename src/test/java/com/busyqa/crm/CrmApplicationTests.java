package com.busyqa.crm;

import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.UserRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrmApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Test
	public void contextLoads() {

		User user = userRepository.findById(Long.valueOf(1)).get();

		assertThat(user.getEmail().equals("administrator@busyqa.com"));

	}

}
