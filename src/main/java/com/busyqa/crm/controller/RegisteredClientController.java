package com.busyqa.crm.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.StatusName;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.CourseRepository;
import com.busyqa.crm.repo.PaymentHistoryRespository;
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
@RequestMapping("/registered-client")
@Configuration
@PropertySource("classpath:application.properties")
public class RegisteredClientController {
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
	@Autowired
	MailClient mailClient;
	@Autowired
	PaymentHistoryRespository paymentHistoryRespository;

	@Value( "${backend.runner}" )
	private String beUrl;



	@PutMapping("/{id}")
	public ApiResponse<Void> update(@RequestBody String clientDto) throws IOException, InterruptedException {


		String url = String.format("%s/registered-client/", beUrl);


		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Couldnt Update CLient", null);
		ObjectMapper mapper = new ObjectMapper();
		Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

		// Saving User
		User updatedUser = userRepository.findByUsername(clientDtoUpdated.getUsername()).get();
		// Saving Payment record
		PaymentRecord deletePaymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findByUsername(clientDtoUpdated.getUsername()).get()).get();
		if (deletePaymentRecordList.getIsRegisteredStudent().equals(StatusName.NO.name())) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Registration Incomplete", null);
		}

		if (deletePaymentRecordList.getIsRegisteredStudent().equals(null)) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Registration Incomplete", null);
		}
		if (deletePaymentRecordList.getStudentLock().equals(StatusName.YES.name())) {
			Client client = new Client();
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
					"Sales Team has Already Approved this client as user", client);
		}
		if (deletePaymentRecordList.getPaySetupDone().equals("No")) {
			Client client = new Client();
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Sales Team hasnt setup the payment", client);
		}
		updatedUser.setStatus(StatusName.YES.name());
		userRepository.save(updatedUser);

		PaymentRecord paymentRecordList = deletePaymentRecordList;

		if (paymentRecordList.getisCourse().equals("Yes")) {
			paymentRecordList.setClientStatus(clientStatusRepository.findByname(ClientStatusName.STUDENT.name()).get());

		} else {
			paymentRecordList.setClientStatus(clientStatusRepository.findByname(ClientStatusName.INTERN.name()).get());
		}

		paymentRecordList.setLastModifiedTimeStamp(LocalDate.now().toString());
		paymentRecordList.setIsRegisteredStudent(StatusName.YES.name());
		paymentRecordList.setStudentLock(StatusName.YES.name());
		paymentRecordRepository.delete(deletePaymentRecordList);
		paymentRecordRepository.save(paymentRecordList);
		mailClient.prepareAndSendPassword(updatedUser.getEmail(), url + updatedUser.getId());
		System.out.println("Password Email Sent");
		return new ApiResponse<>(HttpStatus.OK.value(), "You have enrolled a Student !!!", null);
	}

	@PutMapping("/student/{id}")
	public ApiResponse<Void> updateStudent(@RequestBody String clientDto) throws IOException, InterruptedException {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Couldnt Update CLient", null);
		ObjectMapper mapper = new ObjectMapper();
		Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

		// Saving User
		User updatedUser = userRepository.findByUsername(clientDtoUpdated.getUsername()).get();
		// Saving Payment record
		PaymentRecord deletePaymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findByUsername(clientDtoUpdated.getUsername()).get()).get();

		PaymentRecord paymentRecordList = deletePaymentRecordList;

		if (deletePaymentRecordList.getAmountPaid() >= deletePaymentRecordList.getTotalCourseAmount()) {
			paymentRecordList.setClientStatus(clientStatusRepository.findByname("INTERN").get());
			paymentRecordRepository.delete(deletePaymentRecordList);
			paymentRecordRepository.save(paymentRecordList);
			return new ApiResponse<>(HttpStatus.OK.value(), "Client Status Changed !!!", null);
		} else {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
					"Client Status Not Changed !!!, Check Payment History for more details", null);

		}

	}

	@GetMapping("/{id}")
	public ApiResponse<Client> getOne(@PathVariable int id) {
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
		Client client = new Client();
		client.setId(paymentRecordList.getUser().getId());
		client.setFirstName(paymentRecordList.getUser().getFirstName());
		client.setLastName(paymentRecordList.getUser().getLastName());
		client.setUsername(paymentRecordList.getUser().getUsername());
		client.setEmail(paymentRecordList.getUser().getEmail());
		client.setPassword(paymentRecordList.getUser().getPassword());
		client.setStatusAsOfDay(paymentRecordList.getUser().getStatusAsOfDay());
		client.setStatus(paymentRecordList.getUser().getStatus());
		client.setTeams();
		client.setRoles();
		client.setPhoneNumber(paymentRecordList.getUser().getPhoneNumber());
			client.setAddress(paymentRecordList.getUser().getAddress());
		client.setAddress(paymentRecordList.getUser().getCity());
		client.setState(paymentRecordList.getUser().getState());
		client.setZipCode(paymentRecordList.getUser().getZipCode());
		client.setCountry(paymentRecordList.getUser().getCountry());
		client.setEmergencyPhoneNumber(paymentRecordList.getUser().getEmergencyPhoneNumber());
		client.setAboutUs(paymentRecordList.getUser().getAboutUs());
		client.setClientPassLock(paymentRecordList.getUser().getClientPassLock());
		client.setAmountPaid(paymentRecordList.getAmountPaid());
		client.setTotalCourseAmount(paymentRecordList.getBusyqaBatch().getNetCourseAmount());
		client.setTotalCourseAmountAfterDiscount(paymentRecordList.getTotalCourseAmountAfterDiscount());
		client.setTotaDiscountAmount(paymentRecordList.getTotaDiscountAmount());
		client.setCurrentlyEmployed(paymentRecordList.getCurrentlyEmployed());
		client.setCurrentlyTechIndustry(paymentRecordList.getCurrentlyTechIndustry());
		client.setDesriredJob(paymentRecordList.getDesriredJob());
		client.setCurrentJob(paymentRecordList.getCurrentJob());
		client.setLeadSource(paymentRecordList.getLeadSource());
		client.setStudentComments(paymentRecordList.getStudentComments());
		client.setCreatedTimeStamp(paymentRecordList.getCreatedTimeStamp());
		client.setRegisterationTimeStamp(paymentRecordList.getRegisterationTimeStamp());
		client.setLastModifiedTimeStamp(paymentRecordList.getLastModifiedTimeStamp());
		client.setLastPaymentTimeStamp(paymentRecordList.getLastPaymentTimeStamp());
		client.setStudentLock(paymentRecordList.getStudentLock());
		client.setisCourse(paymentRecordList.getisCourse());
		client.setInternDate(paymentRecordList.getInternDate());
		client.setClientStatus(paymentRecordList.getClientStatus().getName());
		client.setPaymentPlan(paymentRecordList.getPaymentPlan().getName());
		client.setPaymentStatus(paymentRecordList.getPaymentStatus().getName());
		List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository
				.findByuserOrderByScheduledPaymentDateAsc(userRepository.findById(Long.valueOf(id)).get());

		client.setCourseName(paymentRecordList.getBusyqaBatch().getBusyqaCourse().getCourseName());
		client.setCourseLocation(paymentRecordList.getBusyqaBatch().getCourseLocation());
		client.setCourseLenght(paymentRecordList.getBusyqaBatch().getBusyqaCourse().getCourseLenght());
		client.setStartDate(paymentRecordList.getBusyqaBatch().getStartDate());
		client.setEndDate(paymentRecordList.getBusyqaBatch().getEndDate());
		client.setPaySetupDone(paymentRecordList.getPaySetupDone());
		client.setAnchorDate(paymentRecordList.getAnchorDate());
		client.setIsRegisteredStudent(paymentRecordList.getIsRegisteredStudent());
		client.setRegistrationFees(paymentRecordList.getBusyqaBatch().getRegistrationFees());
		client.setSeason(paymentRecordList.getBusyqaBatch().getSeason());
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", client);
	}
}
