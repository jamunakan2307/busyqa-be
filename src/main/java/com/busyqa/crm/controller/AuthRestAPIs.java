package com.busyqa.crm.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.message.request.LoginForm;
import com.busyqa.crm.message.response.JwtResponse;
import com.busyqa.crm.message.response.ResponseMessage;
import com.busyqa.crm.model.BusyqaBatch;
import com.busyqa.crm.model.ClientStatus;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentPlan;
import com.busyqa.crm.model.PaymentPlanName;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.PaymentStatus;
import com.busyqa.crm.model.PaymentStatusName;
import com.busyqa.crm.model.Role;
import com.busyqa.crm.model.RoleName;
import com.busyqa.crm.model.SignUpForm2;
import com.busyqa.crm.model.StatusName;
import com.busyqa.crm.model.Team;
import com.busyqa.crm.model.TeamName;
import com.busyqa.crm.model.UpdateUserDto;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.ClientCourseRepo;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.PaymentHistoryRespository;
import com.busyqa.crm.repo.PaymentPlanRepository;
import com.busyqa.crm.repo.PaymentRecordRepository;
import com.busyqa.crm.repo.PaymentStatusRepository;
import com.busyqa.crm.repo.RoleRepository;
import com.busyqa.crm.repo.TeamRepository;
import com.busyqa.crm.repo.TrainerRespository;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.repo.verion_2_0.BusyqaBatchRepo;
import com.busyqa.crm.repo.verion_2_0.BusyqaCourseRepo;
import com.busyqa.crm.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Configuration
@PropertySource("classpath:application.properties")
public class AuthRestAPIs {

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
	ClientCourseRepo clientCourseRepo;

	@Autowired
	ClientStatusRepository clientStatusRepository;

	@Autowired
	PaymentPlanRepository paymentPlanRepository;

	@Autowired
	PaymentStatusRepository paymentStatusRepository;

	@Autowired
	MailClient mailClient;

	@Autowired
	TrainerRespository trainerRespository;

	@Autowired
	PaymentHistoryRespository paymentHistoryRespository;

	@Autowired
	BusyqaBatchRepo busyqaBatchRepo;

	@Autowired
	BusyqaCourseRepo busyqaCourseRepo;

	@Value( "${backend.runner}" )
	private String beUrl;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername()).get();
		if (user.getStatus().equals("NO")) {
			return new ResponseEntity<>(new ResponseMessage("User Not Registered!"), HttpStatus.BAD_REQUEST);
		} else {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			List<UserTeamRole> userTeamRole = userTeamRoleRepository.findByUser(user);

			List<String> teamList = new ArrayList<>();

			for (int i = 0; i < userTeamRole.size(); i++) {
				teamList.add(userTeamRole.get(i).getTeam().getName());
			}
			return ResponseEntity.ok(new JwtResponse(jwt, teamList, userDetails.getAuthorities()));
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm2 signUpRequest) {
		String url =  String.format("%s/unregistered-client/", beUrl);

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("User Not Registered!!!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// creating user account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getStatus(),
				LocalDateTime.now().toString());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user.setClientPassLock(StatusName.NO.name());
		userRepository.save(user);

		User savedUser = userRepository.findByUsername(signUpRequest.getUsername()).get();
		UserTeamRole userTeamRole = new UserTeamRole();
		Role role = roleRepository.findByName(RoleName.ROLE_CLIENT.name()).get();
		Team team = teamRepository.findByName(TeamName.TEAM_CLIENT.name()).get();
		// Saving in Usr team role Repo
		userTeamRole.setRole(role);
		userTeamRole.setTeam(team);
		userTeamRole.setUser(savedUser);

		try {
			userTeamRoleRepository.save(userTeamRole);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> User Not created!"), HttpStatus.BAD_REQUEST);
		}

		// Saving payment History
		PaymentRecord paymentRecord = new PaymentRecord();
		BusyqaBatch busyqaBatch = busyqaBatchRepo.findById(Long.valueOf(signUpRequest.getBatchId())).get();

		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get();
		paymentRecord.setClientStatus(clientStatus);

		PaymentPlan paymentPlan = paymentPlanRepository.findByname(PaymentPlanName.UNSCHEDULED.name()).get();
		paymentRecord.setPaymentPlan(paymentPlan);

		PaymentStatus paymentStatus = paymentStatusRepository.findByname(PaymentStatusName.UNSCHEDULED.name()).get();
		paymentRecord.setPaymentStatus(paymentStatus);
		paymentRecord.setUser(savedUser);
		paymentRecord.setTeam(team);
		paymentRecord.setStudentLock(StatusName.NO.name());
		paymentRecord.setIsRegisteredStudent(StatusName.NO.name());
		paymentRecord.setCreatedTimeStamp(LocalDateTime.now().toString());

		/// Added as per second request

		paymentRecord.setStudentComments(signUpRequest.getStudentComments());
		paymentRecord.setCreatedTimeStamp(LocalDateTime.now().toString());
		paymentRecord.setLeadSource(signUpRequest.getLeadSource());
		paymentRecord.setPaySetupDone("No");
		paymentRecord.setTotalCourseAmount(busyqaBatch.getNetCourseAmount());
		paymentRecord.setAmountPaid(0);
		paymentRecord.setRegistrationFees(busyqaBatch.getRegistrationFees());
		// Batch
		paymentRecord.setBusyqaBatch(busyqaBatch);

		if (busyqaBatch.getBusyqaCourse().getCourseName().contains("intern") || busyqaBatch.getBusyqaCourse()
				.getCourseName().contains("INTERN") || busyqaBatch.getBusyqaCourse().getCourseName().contains("ern")) {
			paymentRecord.setisCourse("No");
		} else {
			paymentRecord.setisCourse("Yes");
		}
		try {
			paymentRecordRepository.save(paymentRecord);

		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> User record not  created!"),
					HttpStatus.BAD_REQUEST);
		}
		mailClient.prepareAndSend(savedUser.getEmail(), "Thank You for Chosing BusyQA",
				String.format("Welcome to %s Course", busyqaBatch.getBusyqaCourse().getCourseName()),
				busyqaBatch.getUser().getEmail().toUpperCase() + busyqaBatch.getId()+"_batch");
		System.out.println("Signup Email Sent");

		mailClient.prepareAndSend(savedUser.getEmail(), url + savedUser.getId(), busyqaBatch.getUser().getFirstName());
		System.out.println("Registration  Email Sent");


		mailClient.prepareAndSend(savedUser.getEmail(), "Meet Your Trainer",
				String.format("BusyQA Trainer", busyqaBatch.getBusyqaCourse().getCourseName()),
				busyqaBatch.getUser().getEmail().toUpperCase());
		System.out.println("Trainer Email Sent");

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@PostMapping("/adminsignup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody String signupRequest) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		UpdateUserDto signUpRequest = mapper.readValue(signupRequest, UpdateUserDto.class);
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// creating user account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), "YES",
				LocalDateTime.now().toString());
		userRepository.save(user);

		User savedUser = userRepository.findByUsername(signUpRequest.getUsername()).get();

		if (signUpRequest.getRoles().size() == signUpRequest.getTeams().size()) {

			for (int i = 0; i < signUpRequest.getRoles().size(); i++) {
				UserTeamRole userTeamRole = new UserTeamRole();
				userTeamRole.setUser(savedUser);

				Team savedTeam = teamRepository.findByName(signUpRequest.getTeams().get(i).getName())
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Team  not find."));

				Role savedRole = roleRepository.findByName(signUpRequest.getRoles().get(i).getName())
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

				userTeamRole.setRole(savedRole);
				userTeamRole.setTeam(savedTeam);
				userTeamRoleRepository.save(userTeamRole);
			}

		} else {
			userRepository.deleteById(savedUser.getId());
			return new ResponseEntity<>(new ResponseMessage("Mismatch between roles and team assignment !"),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}
