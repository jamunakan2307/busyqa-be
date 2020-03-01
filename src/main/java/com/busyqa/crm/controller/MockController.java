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
import com.busyqa.crm.model.MockRecord;
import com.busyqa.crm.model.MockRecordDto;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.ClientCourseRepo;
import com.busyqa.crm.repo.ClientStatusRepository;
import com.busyqa.crm.repo.InternRecordRepository;
import com.busyqa.crm.repo.MockRecordRepository;
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
@RequestMapping("/mock")
public class MockController {

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
	@Autowired
	MockRecordRepository mockRecordRepository;

	@GetMapping("/{id}")
	public ApiResponse<MockRecordDto> getOne(@PathVariable int id) {

		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
		User user = userRepository.findById(Long.valueOf(id)).get();

		try {
			MockRecordDto mockRecordDto = new MockRecordDto();
			mockRecordDto.setId(paymentRecordList.getUser().getId().intValue());
			mockRecordDto.setFirstName(paymentRecordList.getUser().getFirstName());
			mockRecordDto.setLastName(paymentRecordList.getUser().getLastName());
			mockRecordDto.setEmail(paymentRecordList.getUser().getEmail());
			mockRecordDto.setPhoneNumber(paymentRecordList.getUser().getPhoneNumber());
			mockRecordDto.setClientStatus(paymentRecordList.getClientStatus().getName());
			MockRecord fetchrecord = new MockRecord();
			try {
				fetchrecord = mockRecordRepository.findByuser(paymentRecordList.getUser()).get();

			} catch (Exception e) {
			}
			mockRecordDto.setInterviewDate(fetchrecord.getInterviewDate());

			return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", mockRecordDto);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Use Not fetched", null);
		}
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@PathVariable int id, @RequestBody String clientDto) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String message = null;
			MockRecordDto mockRecordDto = mapper.readValue(clientDto, MockRecordDto.class);

			User user = userRepository.findById(Long.valueOf(id)).get();
			PaymentRecord paymentRecordList = paymentRecordRepository.findByUser(user).get();

			PaymentRecord newPaymentRecordList = paymentRecordList;
			newPaymentRecordList
					.setClientStatus(clientStatusRepository.findByname(mockRecordDto.getClientStatus()).get());
			MockRecord updatedRedord = new MockRecord();
			try {
				MockRecord deleteMockRecord = mockRecordRepository.findByuser(user).get();
				updatedRedord = deleteMockRecord;
				updatedRedord.setInterviewDate(mockRecordDto.getInterviewDate());
				updatedRedord.setUser(user);

			} catch (Exception e) {
				updatedRedord.setInterviewDate(mockRecordDto.getInterviewDate());
				updatedRedord.setUser(user);
			}
			paymentRecordRepository.delete(paymentRecordList);
			paymentRecordRepository.save(newPaymentRecordList);
			mockRecordRepository.save(updatedRedord);
			return new ApiResponse<>(HttpStatus.OK.value(), "Mock Record Saved", null);

		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Cannot Update IUser ", null);
		}
	}

}
