package com.busyqa.crm.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.StatusName;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.CourseRepository;
import com.busyqa.crm.repo.PaymentPlanRepository;
import com.busyqa.crm.repo.PaymentRecordRepository;
import com.busyqa.crm.repo.PaymentStatusRepository;
import com.busyqa.crm.repo.RoleRepository;
import com.busyqa.crm.repo.TeamRepository;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.security.JwtProvider;
import com.busyqa.crm.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/save-client")
public class SaveClientController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	PaymentRecordRepository paymentRecordRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ClientStatusRepository clientStatusRepository;

	@Autowired
	PaymentPlanRepository paymentPlanRepository;

	@Autowired
	PaymentStatusRepository paymentStatusRepository;
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@RequestBody String clientDto) throws IOException {
		String url = "http://localhost:4200/registered-client/";
			ApiResponse<Void> message = null;
		ObjectMapper mapper = new ObjectMapper();
		Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);
		User user = userRepository.findByUsername(clientDtoUpdated.getUsername()).get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (clientDtoUpdated.getPassword().length() < 8) {
			message = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad Password .. retry", null);
		} else {
			if (!user.getClientPassLock().equals("YES")) {
				if (encoder.matches(clientDtoUpdated.getPassword(), user.getPassword())) {
					message = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad Password .. retry", null);
				} else {
					User updatedUser = userRepository.findByUsername(clientDtoUpdated.getUsername()).get();
					updatedUser.setPassword(encoder.encode(clientDtoUpdated.getPassword()));
					updatedUser.setClientPassLock(StatusName.YES.name());
					userRepository.save(updatedUser);
					message = new ApiResponse<>(HttpStatus.OK.value(),
							String.format("Your Password Has been Set !!! And Your Username is %s",
									updatedUser.getUsername()), null);
				}
			} else {
				message = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
						"You cannot set the password as the link is expired ", null);
			}
		}

		return message;
	}
}
