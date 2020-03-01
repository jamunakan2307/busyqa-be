package com.busyqa.crm.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/client")
public class ClientUserController {
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
	PaymentHistoryRespository paymentHistoryRespository;

	@GetMapping("/{userName}")
	public ApiResponse<List<Client>> getAllClients(@PathVariable String userName) {
		List<Client> clientList = new ArrayList<>();
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		User user = userRepository.findByUsername(jwtProvider.getUserNameFromJwtToken(userName)).get();
		PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();
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
		List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository.findByuserOrderByScheduledPaymentDateAsc(
				userRepository.findById(Long.valueOf(paymentRecordList.getUser().getId())).get());

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
		clientList.add(client);
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", clientList);
	}
}
