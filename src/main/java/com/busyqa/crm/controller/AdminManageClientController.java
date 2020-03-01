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
import com.busyqa.crm.model.AluminiRecord;
import com.busyqa.crm.model.AluminiRecordDto;
import com.busyqa.crm.model.Client;
import com.busyqa.crm.model.ClientStatus;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.InterminRecord;
import com.busyqa.crm.model.InternRecord;
import com.busyqa.crm.model.MockRecord;
import com.busyqa.crm.model.MockRecordDto;
import com.busyqa.crm.model.PaymentHistroy;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.ResumeRecord;
import com.busyqa.crm.model.ResumeRecordDto;
import com.busyqa.crm.model.UserTeamRole;
import com.busyqa.crm.repo.AluminiRepository;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.CourseRepository;
import com.busyqa.crm.repo.InternRecordRepository;
import com.busyqa.crm.repo.MockRecordRepository;
import com.busyqa.crm.repo.PaymentHistoryRespository;
import com.busyqa.crm.repo.PaymentPlanRepository;
import com.busyqa.crm.repo.PaymentRecordRepository;
import com.busyqa.crm.repo.PaymentStatusRepository;
import com.busyqa.crm.repo.ResumeRecordRepository;
import com.busyqa.crm.repo.RoleRepository;
import com.busyqa.crm.repo.TeamRepository;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.security.JwtProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/client")
public class AdminManageClientController {
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

	@Autowired
	InternRecordRepository internRecordRepository;

	@Autowired
	ResumeRecordRepository resumeRecordRepository;

	@Autowired
	MockRecordRepository mockRecordRepository;

	@Autowired
	AluminiRepository aluminiRepository;

	@GetMapping("/{userName}")
	public ApiResponse<List<Client>> getAllClients(@PathVariable String userName) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		boolean clientAccess = true;
		com.busyqa.crm.model.User requestorUser = userRepository.findByUsername(userName).get();
		List<UserTeamRole> requestorTeamRoles = userTeamRoleRepository.findByUser(requestorUser);
		List<Client> clientList = new ArrayList<>();

		for (int g = 0; g < requestorTeamRoles.size(); g++) {
			if (requestorTeamRoles.get(g).getTeam().getName().equals("TEAM_SALES")) {
				clientAccess = false;
			}
		}

		for (int g = 0; g < requestorTeamRoles.size(); g++) {
			if (requestorTeamRoles.get(g).getTeam().getName().equals("TEAM_ADMIN") && requestorTeamRoles.get(g)
					.getRole().getName().equals("ROLE_ADMIN")) {
				clientAccess = true;
			}
		}

		if (clientAccess) {
			List<PaymentRecord> paymentRecordList = paymentRecordRepository.findAll();

			for (int i = 0; i < paymentRecordList.size(); i++) {
				Client client = new Client();
				client.setId(paymentRecordList.get(i).getUser().getId());
				client.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
				client.setLastName(paymentRecordList.get(i).getUser().getLastName());
				client.setUsername(paymentRecordList.get(i).getUser().getUsername());
				client.setEmail(paymentRecordList.get(i).getUser().getEmail());
				client.setPassword(paymentRecordList.get(i).getUser().getPassword());
				client.setStatusAsOfDay(paymentRecordList.get(i).getUser().getStatusAsOfDay());
				client.setStatus(paymentRecordList.get(i).getUser().getStatus());
				client.setTeams();
				client.setRoles();
				client.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
				client.setAddress(paymentRecordList.get(i).getUser().getAddress());
				client.setAddress(paymentRecordList.get(i).getUser().getCity());
				client.setState(paymentRecordList.get(i).getUser().getState());
				client.setZipCode(paymentRecordList.get(i).getUser().getZipCode());
				client.setCountry(paymentRecordList.get(i).getUser().getCountry());
				client.setEmergencyPhoneNumber(paymentRecordList.get(i).getUser().getEmergencyPhoneNumber());
				client.setAboutUs(paymentRecordList.get(i).getUser().getAboutUs());
				client.setClientPassLock(paymentRecordList.get(i).getUser().getClientPassLock());
				client.setAmountPaid(paymentRecordList.get(i).getAmountPaid());
				client.setTotalCourseAmount(paymentRecordList.get(i).getBusyqaBatch().getNetCourseAmount());
				client.setTotalCourseAmountAfterDiscount(paymentRecordList.get(i).getTotalCourseAmountAfterDiscount());
				client.setTotaDiscountAmount(paymentRecordList.get(i).getTotaDiscountAmount());
				client.setCurrentlyEmployed(paymentRecordList.get(i).getCurrentlyEmployed());
				client.setCurrentlyTechIndustry(paymentRecordList.get(i).getCurrentlyTechIndustry());
				client.setDesriredJob(paymentRecordList.get(i).getDesriredJob());
				client.setCurrentJob(paymentRecordList.get(i).getCurrentJob());
				client.setLeadSource(paymentRecordList.get(i).getLeadSource());
				client.setStudentComments(paymentRecordList.get(i).getStudentComments());
				client.setCreatedTimeStamp(paymentRecordList.get(i).getCreatedTimeStamp());
				client.setRegisterationTimeStamp(paymentRecordList.get(i).getRegisterationTimeStamp());
				client.setLastModifiedTimeStamp(paymentRecordList.get(i).getLastModifiedTimeStamp());
				client.setLastPaymentTimeStamp(paymentRecordList.get(i).getLastPaymentTimeStamp());
				client.setStudentLock(paymentRecordList.get(i).getStudentLock());
				client.setisCourse(paymentRecordList.get(i).getisCourse());
				client.setInternDate(paymentRecordList.get(i).getInternDate());
				client.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
				client.setPaymentPlan(paymentRecordList.get(i).getPaymentPlan().getName());
				client.setPaymentStatus(paymentRecordList.get(i).getPaymentStatus().getName());
				List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository
						.findByuserOrderByScheduledPaymentDateAsc(
								userRepository.findById(Long.valueOf(paymentRecordList.get(i).getUser().getId()))
										.get());

				client.setCourseName(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseName());
				client.setCourseLocation(paymentRecordList.get(i).getBusyqaBatch().getCourseLocation());
				client.setCourseLenght(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseLenght());
				client.setStartDate(paymentRecordList.get(i).getBusyqaBatch().getStartDate());
				client.setEndDate(paymentRecordList.get(i).getBusyqaBatch().getEndDate());
				client.setPaySetupDone(paymentRecordList.get(i).getPaySetupDone());
				client.setAnchorDate(paymentRecordList.get(i).getAnchorDate());
				client.setIsRegisteredStudent(paymentRecordList.get(i).getIsRegisteredStudent());

				client.setRegistrationFees(paymentRecordList.get(i).getBusyqaBatch().getRegistrationFees());
				client.setSeason(paymentRecordList.get(i).getBusyqaBatch().getSeason());
				clientList.add(client);
			}
		} else {
			ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get();
			List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);

			for (int i = 0; i < paymentRecordList.size(); i++) {
				Client client = new Client();
				client.setId(paymentRecordList.get(i).getUser().getId());
				client.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
				client.setLastName(paymentRecordList.get(i).getUser().getLastName());
				client.setUsername(paymentRecordList.get(i).getUser().getUsername());
				client.setEmail(paymentRecordList.get(i).getUser().getEmail());
				client.setPassword(paymentRecordList.get(i).getUser().getPassword());
				client.setStatusAsOfDay(paymentRecordList.get(i).getUser().getStatusAsOfDay());
				client.setStatus(paymentRecordList.get(i).getUser().getStatus());
				client.setTeams();
				client.setRoles();
				client.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
				client.setAddress(paymentRecordList.get(i).getUser().getAddress());
				client.setAddress(paymentRecordList.get(i).getUser().getCity());
				client.setState(paymentRecordList.get(i).getUser().getState());
				client.setZipCode(paymentRecordList.get(i).getUser().getZipCode());
				client.setCountry(paymentRecordList.get(i).getUser().getCountry());
				client.setEmergencyPhoneNumber(paymentRecordList.get(i).getUser().getEmergencyPhoneNumber());
				client.setAboutUs(paymentRecordList.get(i).getUser().getAboutUs());
				client.setClientPassLock(paymentRecordList.get(i).getUser().getClientPassLock());
				client.setAmountPaid(paymentRecordList.get(i).getAmountPaid());
				client.setTotalCourseAmount(paymentRecordList.get(i).getBusyqaBatch().getNetCourseAmount());
				client.setTotalCourseAmountAfterDiscount(paymentRecordList.get(i).getTotalCourseAmountAfterDiscount());
				client.setTotaDiscountAmount(paymentRecordList.get(i).getTotaDiscountAmount());
				client.setCurrentlyEmployed(paymentRecordList.get(i).getCurrentlyEmployed());
				client.setCurrentlyTechIndustry(paymentRecordList.get(i).getCurrentlyTechIndustry());
				client.setDesriredJob(paymentRecordList.get(i).getDesriredJob());
				client.setCurrentJob(paymentRecordList.get(i).getCurrentJob());
				client.setLeadSource(paymentRecordList.get(i).getLeadSource());
				client.setStudentComments(paymentRecordList.get(i).getStudentComments());
				client.setCreatedTimeStamp(paymentRecordList.get(i).getCreatedTimeStamp());
				client.setRegisterationTimeStamp(paymentRecordList.get(i).getRegisterationTimeStamp());
				client.setLastModifiedTimeStamp(paymentRecordList.get(i).getLastModifiedTimeStamp());
				client.setLastPaymentTimeStamp(paymentRecordList.get(i).getLastPaymentTimeStamp());
				client.setStudentLock(paymentRecordList.get(i).getStudentLock());
				client.setisCourse(paymentRecordList.get(i).getisCourse());
				client.setInternDate(paymentRecordList.get(i).getInternDate());
				client.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
				client.setPaymentPlan(paymentRecordList.get(i).getPaymentPlan().getName());
				client.setPaymentStatus(paymentRecordList.get(i).getPaymentStatus().getName());
				List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository
						.findByuserOrderByScheduledPaymentDateAsc(
								userRepository.findById(Long.valueOf(paymentRecordList.get(i).getUser().getId()))
										.get());

				client.setCourseName(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseName());
				client.setCourseLocation(paymentRecordList.get(i).getBusyqaBatch().getCourseLocation());
				client.setCourseLenght(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseLenght());
				client.setStartDate(paymentRecordList.get(i).getBusyqaBatch().getStartDate());
				client.setEndDate(paymentRecordList.get(i).getBusyqaBatch().getEndDate());
				client.setPaySetupDone(paymentRecordList.get(i).getPaySetupDone());
				client.setAnchorDate(paymentRecordList.get(i).getAnchorDate());
				client.setIsRegisteredStudent(paymentRecordList.get(i).getIsRegisteredStudent());
				client.setRegistrationFees(paymentRecordList.get(i).getBusyqaBatch().getRegistrationFees());
				client.setSeason(paymentRecordList.get(i).getBusyqaBatch().getSeason());
				clientList.add(client);
			}
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", clientList);
	}

	@GetMapping("/leads")
	public ApiResponse<List<Client>> getAllLeadsClients() {
		List<Client> clientList = new ArrayList<>();

		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);

		for (int i = 0; i < paymentRecordList.size(); i++) {
			Client client = new Client();
			client.setId(paymentRecordList.get(i).getUser().getId());
			client.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			client.setLastName(paymentRecordList.get(i).getUser().getLastName());
			client.setUsername(paymentRecordList.get(i).getUser().getUsername());
			client.setEmail(paymentRecordList.get(i).getUser().getEmail());
			client.setPassword(paymentRecordList.get(i).getUser().getPassword());
			client.setStatusAsOfDay(paymentRecordList.get(i).getUser().getStatusAsOfDay());
			client.setStatus(paymentRecordList.get(i).getUser().getStatus());
			client.setTeams();
			client.setRoles();
			client.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			client.setAddress(paymentRecordList.get(i).getUser().getAddress());
			client.setAddress(paymentRecordList.get(i).getUser().getCity());
			client.setState(paymentRecordList.get(i).getUser().getState());
			client.setZipCode(paymentRecordList.get(i).getUser().getZipCode());
			client.setCountry(paymentRecordList.get(i).getUser().getCountry());
			client.setEmergencyPhoneNumber(paymentRecordList.get(i).getUser().getEmergencyPhoneNumber());
			client.setAboutUs(paymentRecordList.get(i).getUser().getAboutUs());
			client.setClientPassLock(paymentRecordList.get(i).getUser().getClientPassLock());
			client.setAmountPaid(paymentRecordList.get(i).getAmountPaid());
			client.setTotalCourseAmount(paymentRecordList.get(i).getBusyqaBatch().getNetCourseAmount());
			client.setTotalCourseAmountAfterDiscount(paymentRecordList.get(i).getTotalCourseAmountAfterDiscount());
			client.setTotaDiscountAmount(paymentRecordList.get(i).getTotaDiscountAmount());
			client.setCurrentlyEmployed(paymentRecordList.get(i).getCurrentlyEmployed());
			client.setCurrentlyTechIndustry(paymentRecordList.get(i).getCurrentlyTechIndustry());
			client.setDesriredJob(paymentRecordList.get(i).getDesriredJob());
			client.setCurrentJob(paymentRecordList.get(i).getCurrentJob());
			client.setLeadSource(paymentRecordList.get(i).getLeadSource());
			client.setStudentComments(paymentRecordList.get(i).getStudentComments());
			client.setCreatedTimeStamp(paymentRecordList.get(i).getCreatedTimeStamp());
			client.setRegisterationTimeStamp(paymentRecordList.get(i).getRegisterationTimeStamp());
			client.setLastModifiedTimeStamp(paymentRecordList.get(i).getLastModifiedTimeStamp());
			client.setLastPaymentTimeStamp(paymentRecordList.get(i).getLastPaymentTimeStamp());
			client.setStudentLock(paymentRecordList.get(i).getStudentLock());
			client.setisCourse(paymentRecordList.get(i).getisCourse());
			client.setInternDate(paymentRecordList.get(i).getInternDate());
			client.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			client.setPaymentPlan(paymentRecordList.get(i).getPaymentPlan().getName());
			client.setPaymentStatus(paymentRecordList.get(i).getPaymentStatus().getName());
			List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository
					.findByuserOrderByScheduledPaymentDateAsc(
							userRepository.findById(Long.valueOf(paymentRecordList.get(i).getUser().getId())).get());

			client.setCourseName(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseName());
			client.setCourseLocation(paymentRecordList.get(i).getBusyqaBatch().getCourseLocation());
			client.setCourseLenght(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseLenght());
			client.setStartDate(paymentRecordList.get(i).getBusyqaBatch().getStartDate());
			client.setEndDate(paymentRecordList.get(i).getBusyqaBatch().getEndDate());
			client.setPaySetupDone(paymentRecordList.get(i).getPaySetupDone());
			client.setAnchorDate(paymentRecordList.get(i).getAnchorDate());
			client.setIsRegisteredStudent(paymentRecordList.get(i).getIsRegisteredStudent());
			client.setRegistrationFees(paymentRecordList.get(i).getBusyqaBatch().getRegistrationFees());
			client.setSeason(paymentRecordList.get(i).getBusyqaBatch().getSeason());
			clientList.add(client);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", clientList);
	}

	@GetMapping("/students")
	public ApiResponse<List<Client>> getAllStudentssClients() {
		List<Client> clientList = new ArrayList<>();

		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.STUDENT.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);

		for (int i = 0; i < paymentRecordList.size(); i++) {
			Client client = new Client();
			client.setId(paymentRecordList.get(i).getUser().getId());
			client.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			client.setLastName(paymentRecordList.get(i).getUser().getLastName());
			client.setUsername(paymentRecordList.get(i).getUser().getUsername());
			client.setEmail(paymentRecordList.get(i).getUser().getEmail());
			client.setPassword(paymentRecordList.get(i).getUser().getPassword());
			client.setStatusAsOfDay(paymentRecordList.get(i).getUser().getStatusAsOfDay());
			client.setStatus(paymentRecordList.get(i).getUser().getStatus());
			client.setTeams();
			client.setRoles();
			client.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			client.setAddress(paymentRecordList.get(i).getUser().getAddress());
			client.setAddress(paymentRecordList.get(i).getUser().getCity());
			client.setState(paymentRecordList.get(i).getUser().getState());
			client.setZipCode(paymentRecordList.get(i).getUser().getZipCode());
			client.setCountry(paymentRecordList.get(i).getUser().getCountry());
			client.setEmergencyPhoneNumber(paymentRecordList.get(i).getUser().getEmergencyPhoneNumber());
			client.setAboutUs(paymentRecordList.get(i).getUser().getAboutUs());
			client.setClientPassLock(paymentRecordList.get(i).getUser().getClientPassLock());
			client.setAmountPaid(paymentRecordList.get(i).getAmountPaid());
			client.setTotalCourseAmount(paymentRecordList.get(i).getBusyqaBatch().getNetCourseAmount());
			client.setTotalCourseAmountAfterDiscount(paymentRecordList.get(i).getTotalCourseAmountAfterDiscount());
			client.setTotaDiscountAmount(paymentRecordList.get(i).getTotaDiscountAmount());
			client.setCurrentlyEmployed(paymentRecordList.get(i).getCurrentlyEmployed());
			client.setCurrentlyTechIndustry(paymentRecordList.get(i).getCurrentlyTechIndustry());
			client.setDesriredJob(paymentRecordList.get(i).getDesriredJob());
			client.setCurrentJob(paymentRecordList.get(i).getCurrentJob());
			client.setLeadSource(paymentRecordList.get(i).getLeadSource());
			client.setStudentComments(paymentRecordList.get(i).getStudentComments());
			client.setCreatedTimeStamp(paymentRecordList.get(i).getCreatedTimeStamp());
			client.setRegisterationTimeStamp(paymentRecordList.get(i).getRegisterationTimeStamp());
			client.setLastModifiedTimeStamp(paymentRecordList.get(i).getLastModifiedTimeStamp());
			client.setLastPaymentTimeStamp(paymentRecordList.get(i).getLastPaymentTimeStamp());
			client.setStudentLock(paymentRecordList.get(i).getStudentLock());
			client.setisCourse(paymentRecordList.get(i).getisCourse());
			client.setInternDate(paymentRecordList.get(i).getInternDate());
			client.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			client.setPaymentPlan(paymentRecordList.get(i).getPaymentPlan().getName());
			client.setPaymentStatus(paymentRecordList.get(i).getPaymentStatus().getName());
			List<PaymentHistroy> paymentHistroyList = paymentHistoryRespository
					.findByuserOrderByScheduledPaymentDateAsc(
							userRepository.findById(Long.valueOf(paymentRecordList.get(i).getUser().getId())).get());

			client.setCourseName(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseName());
			client.setCourseLocation(paymentRecordList.get(i).getBusyqaBatch().getCourseLocation());
			client.setCourseLenght(paymentRecordList.get(i).getBusyqaBatch().getBusyqaCourse().getCourseLenght());
			client.setStartDate(paymentRecordList.get(i).getBusyqaBatch().getStartDate());
			client.setEndDate(paymentRecordList.get(i).getBusyqaBatch().getEndDate());
			client.setPaySetupDone(paymentRecordList.get(i).getPaySetupDone());
			client.setAnchorDate(paymentRecordList.get(i).getAnchorDate());
			client.setIsRegisteredStudent(paymentRecordList.get(i).getIsRegisteredStudent());
			client.setRegistrationFees(paymentRecordList.get(i).getBusyqaBatch().getRegistrationFees());
			client.setSeason(paymentRecordList.get(i).getBusyqaBatch().getSeason());
			clientList.add(client);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", clientList);
	}

	@GetMapping("/interns")
	public ApiResponse<List<InterminRecord>> getAllInternsClients() {
		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.INTERN.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);
		List<InterminRecord> interminRecordList = new ArrayList<>();

		for (int i = 0; i < paymentRecordList.size(); i++) {
			InterminRecord interminRecord = new InterminRecord();
			interminRecord.setId(paymentRecordList.get(i).getUser().getId().intValue());
			interminRecord.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			interminRecord.setLastName(paymentRecordList.get(i).getUser().getLastName());
			interminRecord.setEmail(paymentRecordList.get(i).getUser().getEmail());
			interminRecord.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			interminRecord.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			try {
				InternRecord fectchreocrd = internRecordRepository.findByuser(paymentRecordList.get(i).getUser()).get();
				interminRecord.setCoopEndDate(fectchreocrd.getCoopEndDate());
				interminRecord.setCoopStarDate(fectchreocrd.getCoopStarDate());
				interminRecord.setProjectAssignment(fectchreocrd.getProjectAssignment());
				interminRecord.setPerformance(fectchreocrd.getPerformance());
			} catch (Exception e) {

			}
			interminRecordList.add(interminRecord);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", interminRecordList);
	}

	@GetMapping("/resume")
	public ApiResponse<List<InterminRecord>> getAllResumeClients() {
		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.RESUME.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);
		List<ResumeRecordDto> resumeRecordlist = new ArrayList<>();

		for (int i = 0; i < paymentRecordList.size(); i++) {
			ResumeRecordDto resumeRecordDto = new ResumeRecordDto();
			resumeRecordDto.setId(paymentRecordList.get(i).getUser().getId().intValue());
			resumeRecordDto.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			resumeRecordDto.setLastName(paymentRecordList.get(i).getUser().getLastName());
			resumeRecordDto.setEmail(paymentRecordList.get(i).getUser().getEmail());
			resumeRecordDto.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			resumeRecordDto.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			ResumeRecord fetchrecord = new ResumeRecord();
			try {
				fetchrecord = resumeRecordRepository.findByuser(paymentRecordList.get(i).getUser()).get();

			} catch (Exception e) {
			}
			resumeRecordDto.setDateStarted(fetchrecord.getDateStarted());
			resumeRecordDto.setDateCompleted(fetchrecord.getDateCompleted());
			resumeRecordlist.add(resumeRecordDto);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", resumeRecordlist);
	}

	@GetMapping("/mock")
	public ApiResponse<List<InterminRecord>> getAllMockClients() {
		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.MOCK.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);
		List<MockRecordDto> mockRecordlist = new ArrayList<>();

		for (int i = 0; i < paymentRecordList.size(); i++) {
			MockRecordDto mockRecordDto = new MockRecordDto();
			mockRecordDto.setId(paymentRecordList.get(i).getUser().getId().intValue());
			mockRecordDto.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			mockRecordDto.setLastName(paymentRecordList.get(i).getUser().getLastName());
			mockRecordDto.setEmail(paymentRecordList.get(i).getUser().getEmail());
			mockRecordDto.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			mockRecordDto.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			MockRecord fetchrecord = new MockRecord();
			try {
				fetchrecord = mockRecordRepository.findByuser(paymentRecordList.get(i).getUser()).get();

			} catch (Exception e) {
			}
			mockRecordDto.setInterviewDate(fetchrecord.getInterviewDate());
			mockRecordlist.add(mockRecordDto);
		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", mockRecordlist);
	}

	@GetMapping("/alumini")
	public ApiResponse<List<Client>> getAllAluminiClients() {

		ClientStatus clientStatus = clientStatusRepository.findByname(ClientStatusName.ALUMINI.name()).get();
		List<PaymentRecord> paymentRecordList = paymentRecordRepository.findByClientStatus(clientStatus);
		List<AluminiRecordDto> aluminiRecordList = new ArrayList<>();

		for (int i = 0; i < paymentRecordList.size(); i++) {

			AluminiRecordDto aluminiRecordDto = new AluminiRecordDto();
			aluminiRecordDto.setId(paymentRecordList.get(i).getUser().getId().intValue());
			aluminiRecordDto.setFirstName(paymentRecordList.get(i).getUser().getFirstName());
			aluminiRecordDto.setLastName(paymentRecordList.get(i).getUser().getLastName());
			aluminiRecordDto.setEmail(paymentRecordList.get(i).getUser().getEmail());
			aluminiRecordDto.setPhoneNumber(paymentRecordList.get(i).getUser().getPhoneNumber());
			aluminiRecordDto.setClientStatus(paymentRecordList.get(i).getClientStatus().getName());
			AluminiRecord fetchrecord = new AluminiRecord();
			try {
				AluminiRecord deleteAluminiRecord = aluminiRepository.findByuser(paymentRecordList.get(i).getUser())
						.get();
				fetchrecord = deleteAluminiRecord;
				aluminiRecordDto.setCompanyName(fetchrecord.getCompanyName());
				aluminiRecordDto.setEmployementType(fetchrecord.getEmployementType());
				aluminiRecordDto.setAluminiDesignation(fetchrecord.getAluminiDesignation());
				aluminiRecordDto.setAluminiComments(fetchrecord.getAluminiComments());
				aluminiRecordDto.setPlacementStatus(fetchrecord.getPlacementStatus());
			} catch (Exception e) {
				aluminiRecordDto.setCompanyName(fetchrecord.getCompanyName());
				aluminiRecordDto.setEmployementType(fetchrecord.getEmployementType());
				aluminiRecordDto.setAluminiDesignation(fetchrecord.getAluminiDesignation());
				aluminiRecordDto.setAluminiComments(fetchrecord.getAluminiComments());
				aluminiRecordDto.setPlacementStatus(fetchrecord.getPlacementStatus());
			}
			aluminiRecordList.add(aluminiRecordDto);

		}
		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", aluminiRecordList);
	}

	@GetMapping("/OneClient/{id}")
	public ApiResponse<Client> getOne(@PathVariable int id) {
		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"You have already registered With Us...", null);

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


