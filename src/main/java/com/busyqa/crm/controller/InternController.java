package com.busyqa.crm.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.busyqa.crm.model.InterminRecord;
import com.busyqa.crm.model.InternRecord;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.ResumeRecord;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.ClientCourseRepo;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.InternRecordRepository;
import com.busyqa.crm.repo.PaymentHistoryRespository;
import com.busyqa.crm.repo.PaymentPlanRepository;
import com.busyqa.crm.repo.PaymentRecordRepository;
import com.busyqa.crm.repo.PaymentStatusRepository;
import com.busyqa.crm.repo.ResumeRecordRepository;
import com.busyqa.crm.repo.RoleRepository;
import com.busyqa.crm.repo.TeamRepository;
import com.busyqa.crm.repo.TrainerRespository;
import com.busyqa.crm.repo.UserRepository;
import com.busyqa.crm.repo.UserTeamRoleRepository;
import com.busyqa.crm.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/intern")
public class InternController {
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
	InternRecordRepository internRecordRepository;

	@Autowired
	ResumeRecordRepository resumeRecordRepository;

	@GetMapping("/{id}")
	public ApiResponse<InterminRecord> getOne(@PathVariable int id) {
		InterminRecord interminRecord = new InterminRecord();

		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
		User user = userRepository.findById(Long.valueOf(id)).get();

		interminRecord.setFirstName(user.getFirstName());
		interminRecord.setId(paymentRecordList.getUser().getId().intValue());
		interminRecord.setFirstName(paymentRecordList.getUser().getFirstName());
		interminRecord.setLastName(paymentRecordList.getUser().getLastName());
		interminRecord.setEmail(paymentRecordList.getUser().getEmail());
		interminRecord.setPhoneNumber(paymentRecordList.getUser().getPhoneNumber());
		interminRecord.setClientStatus(paymentRecordList.getClientStatus().getName());
		try {
			InternRecord fectchreocrd = internRecordRepository.findByuser(user).get();
			interminRecord.setCoopEndDate(fectchreocrd.getCoopEndDate());
			interminRecord.setCoopStarDate(fectchreocrd.getCoopStarDate());
			interminRecord.setProjectAssignment(fectchreocrd.getProjectAssignment());
			interminRecord.setPerformance(fectchreocrd.getPerformance());
		} catch (Exception e) {

		}

		return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", interminRecord);
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@PathVariable int id, @RequestBody String clientDto) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String message = null;
			InterminRecord interminRecord = mapper.readValue(clientDto, InterminRecord.class);

			User user = userRepository.findById(Long.valueOf(id)).get();
			PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();

			PaymentRecord newPaymentRecordList = paymentRecordList;
			newPaymentRecordList
					.setClientStatus(clientStatusRepository.findByname(interminRecord.getClientStatus()).get());
			InternRecord updatedRedord = new InternRecord();
			try {
				InternRecord deleteInternRecord = internRecordRepository.findByuser(user).get();
				updatedRedord = deleteInternRecord;
				updatedRedord.setPerformance(interminRecord.getPerformance());
				updatedRedord.setCoopStarDate(interminRecord.getCoopStarDate());
				updatedRedord.setCoopEndDate(interminRecord.getCoopStarDate());
				updatedRedord.setProjectAssignment(interminRecord.getProjectAssignment());
				updatedRedord.setUser(user);

			} catch (Exception e) {
				updatedRedord.setPerformance(interminRecord.getPerformance());
				updatedRedord.setCoopStarDate(interminRecord.getCoopStarDate());
				updatedRedord.setCoopEndDate(interminRecord.getCoopStarDate());
				updatedRedord.setProjectAssignment(interminRecord.getProjectAssignment());
				updatedRedord.setUser(user);
			}

			try {
				ResumeRecord resumeRecord = resumeRecordRepository.findByuser(user).get();
				resumeRecord.setUser(user);
				resumeRecord.setDateStarted(interminRecord.getCoopEndDate());
				resumeRecordRepository.save(resumeRecord);

			} catch (Exception e) {
				ResumeRecord resumeRecord = new ResumeRecord();
				resumeRecord.setUser(user);
				resumeRecord.setDateStarted(interminRecord.getCoopEndDate());
				resumeRecordRepository.save(resumeRecord);
			}

			paymentRecordRepository.delete(paymentRecordList);
			paymentRecordRepository.save(newPaymentRecordList);
			internRecordRepository.save(updatedRedord);
			return new ApiResponse<>(HttpStatus.OK.value(), "Internship Record Added", null);

		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Cannot Update Intern ", null);

		}

	}
}
