package com.busyqa.crm.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.busyqa.crm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.busyqa.crm.message.response.ApiResponse;
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
import com.busyqa.crm.security.services.FileService;
import com.busyqa.crm.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/unregistered-client")
public class UnregisteredClientController {
	private final FileService fileService;

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


	private boolean filecheck = true;

	@Autowired
	public UnregisteredClientController(FileService fileService) {
		this.fileService = fileService;
	}

	@GetMapping("/{id}")
	public ApiResponse<Client> getOne(@PathVariable int id) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"You have already registered With Us...", null);
		try {
			PaymentRecord paymentRecordList = paymentRecordRepository
					.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
			if (paymentRecordList.getIsRegisteredStudent().equals("YES")) {
				Client client = new Client();
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "You have already registered With Us...",
						client);
			} else {
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
				client.setRegistrationFees(paymentRecordList.getBusyqaBatch().getRegistrationFees());
				client.setSeason(paymentRecordList.getBusyqaBatch().getSeason());
				return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", client);
			}
		} catch (Exception ex) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
					"You have either registered or records not available.", null);
		}
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@PathVariable int id, @RequestBody String clientDto) throws IOException {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Registration Incomplete", null);
		ObjectMapper mapper = new ObjectMapper();
		String message = null;
		Client clientDtoUpdated = mapper.readValue(clientDto, Client.class);

		// Saving User
		try {

			User updatedUser = userRepository.findById(Long.valueOf(id)).get();
			updatedUser.setAddress(clientDtoUpdated.getAddress());
			updatedUser.setCountry(clientDtoUpdated.getCountry());
			updatedUser.setCity(clientDtoUpdated.getCity());
			updatedUser.setState(clientDtoUpdated.getState());
			updatedUser.setZipCode(clientDtoUpdated.getZipCode());
			updatedUser.setEmergencyPhoneNumber(clientDtoUpdated.getEmergencyPhoneNumber());
			updatedUser.setStatus(StatusName.NO.name());
			User savedUser = userRepository.save(updatedUser);
			PaymentRecord deletePaymentRecordList = paymentRecordRepository
					.findByUser(savedUser).get();

			// Saving Payment record
			PaymentRecord paymentRecordList = deletePaymentRecordList;

			paymentRecordList.setIsRegisteredStudent("YES");
			paymentRecordList.setStudentLock(StatusName.NO.name());
			paymentRecordList.setCurrentlyEmployed(clientDtoUpdated.getCurrentlyEmployed());
			paymentRecordList.setCurrentlyTechIndustry(clientDtoUpdated.getCurrentlyTechIndustry());
			paymentRecordList.setDesriredJob(clientDtoUpdated.getDesriredJob());
			paymentRecordList.setInternDate(clientDtoUpdated.getInternDate());
			paymentRecordList.setCurrentJob(clientDtoUpdated.getCurrentJob());
			paymentRecordList.setPaymentPlan(paymentPlanRepository.findByname(clientDtoUpdated.getPaymentPlan()).get());
			paymentRecordList.setRegisterationTimeStamp(LocalDateTime.now().toString());
			if (filecheck) {
				paymentRecordRepository.delete(deletePaymentRecordList);
				paymentRecordRepository.save(paymentRecordList);
				return new ApiResponse<>(HttpStatus.OK.value(), "Registration Successful", null);
			} else {
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
						"Required Registration Document  File Not Found ", null);
			}
		} catch (Exception ex) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Registration Not Completed", null);
		}
	}

	@PostMapping("/{id}")
	public ApiResponse<Void> handleFileUpload(@PathVariable String id, @RequestParam("file") MultipartFile file)
			throws IOException {
		try {
			User user = userRepository.findById(Long.valueOf(id)).get();
			if (file.getContentType().equals("application/pdf")) {
				fileService.storeAgreeFile(file, user.getUsername() + "_Registration_AGREE", ".pdf");
				return new ApiResponse<>(HttpStatus.OK.value(), "Payment Agreement... uploaded", null);
			} else {
				filecheck = false;
				return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Please upload Files in PDF format  ...",
						null);

			}

		} catch (Exception e) {
			filecheck = false;
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Error in uploading file ...", null);
		}
	}
}
