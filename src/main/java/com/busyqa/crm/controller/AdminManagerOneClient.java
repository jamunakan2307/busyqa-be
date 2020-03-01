package com.busyqa.crm.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.StatusName;
import com.busyqa.crm.model.User;
import com.busyqa.crm.model.UserTeamRole;
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
@RequestMapping("/admin/oneclient")
public class  AdminManagerOneClient {
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
	PaymentHistoryRespository paymentHistoryRespository;

	@GetMapping("/{id}")
	public ApiResponse<Client> getOne(@PathVariable int id) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();

		User user = userRepository.findById(Long.valueOf(id)).get();
		if (user.getStatus().equals(StatusName.NO.name())) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Not Approved By Sales Team ", null);
		}
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

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@RequestBody String clientDto) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String message = null;
		int differenceAmount;
		Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);
		PaymentRecord deletePaymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findByUsername(clientDtoUpdated.getUsername()).get()).get();

		User user = userRepository.findByUsername(clientDtoUpdated.getUsername()).get();

		if (deletePaymentRecordList.getIsRegisteredStudent().equals(StatusName.NO.name())) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Registration Incomplete", null);
		}

		if (deletePaymentRecordList.getIsRegisteredStudent().equals(null)) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Registration Incomplete", null);
		}

		if (user.getStatus().equals(StatusName.NO.name())) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Client Not Approved By Sales Team ", null);
		}
		PaymentRecord paymentRecordList = deletePaymentRecordList;
		paymentRecordList.setClientStatus(clientStatusRepository.findByname(clientDtoUpdated.getClientStatus()).get());
		paymentRecordList.setPaymentPlan(paymentPlanRepository.findByname(clientDtoUpdated.getPaymentPlan()).get());
		paymentRecordList
				.setPaymentStatus(paymentStatusRepository.findByname(clientDtoUpdated.getPaymentStatus()).get());

		if (paymentRecordList.getClientStatus().getName().equals(ClientStatusName.STUDENT.name())) {
			if (((int) paymentRecordList.getAmountPaid()) < 300) {
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User not updated as initial amount not paid ",
						null);
			} else {
				paymentRecordRepository.delete(deletePaymentRecordList);
				paymentRecordRepository.save(paymentRecordList);
				message = "User updated successfully.";
				return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", null);
			}
		} else if (paymentRecordList.getClientStatus().getName().equals(ClientStatusName.INTERN.name())) {
			differenceAmount = (int) (paymentRecordList.getTotalCourseAmount() - paymentRecordList
					.getAmountPaid()); //To be CHanged
			if (differenceAmount > 0) {
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User not updated as amount not paid in full",
						null);
			} else {
				paymentRecordRepository.delete(deletePaymentRecordList);
				paymentRecordRepository.save(paymentRecordList);
				message = "User updated successfully.";
				return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", null);
			}
		} else if (paymentRecordList.getClientStatus().getName().equals(ClientStatusName.ALUMINI.name())) {
			differenceAmount = (int) (paymentRecordList.getTotalCourseAmount() - paymentRecordList.getAmountPaid());
			if (differenceAmount > 0) {
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "User not updated as amount not paid in full",
						null);
			} else {
				paymentRecordRepository.delete(deletePaymentRecordList);
				paymentRecordRepository.save(paymentRecordList);
				message = "User updated successfully.";
				return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", null);
			}
		} else {
			paymentRecordRepository.delete(deletePaymentRecordList);
			paymentRecordRepository.save(paymentRecordList);
			message = "User updated successfully.";
			return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", null);
		}

	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable int id) {
		com.busyqa.crm.model.User user = userRepository.findById(Long.valueOf(id)).get();
		PaymentRecord deletePaymentRecordList = paymentRecordRepository.findByUser(user).get();
		paymentRecordRepository.delete(deletePaymentRecordList);
		List<UserTeamRole> userTeamRoleList = userTeamRoleRepository.findByUser(user);
		for (int i = 0; i < userTeamRoleList.size(); i++) {
			userTeamRoleRepository.delete(userTeamRoleList.get(i));
		}
		userRepository.delete(user);

		return new ApiResponse<>(HttpStatus.OK.value(), "User successfully Deleted.", null);
	}
}
