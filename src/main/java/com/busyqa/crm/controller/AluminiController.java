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
import com.busyqa.crm.model.AluminiRecord;
import com.busyqa.crm.model.AluminiRecordDto;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.User;
import com.busyqa.crm.repo.AluminiRepository;
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
@RequestMapping("/alumini")
public class AluminiController {
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
	@Autowired
	AluminiRepository aluminiRepository;

	@GetMapping("/{id}")
	public ApiResponse<AluminiRecordDto> getOne(@PathVariable int id) {

		ApiResponse<com.busyqa.crm.model.User> userApiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
				"Users Not found", null);
		PaymentRecord paymentRecordList = paymentRecordRepository
				.findByUser(userRepository.findById(Long.valueOf(id)).get()).get();
		User user = userRepository.findById(Long.valueOf(id)).get();

		try {
			AluminiRecordDto aluminiRecordDto = new AluminiRecordDto();
			aluminiRecordDto.setId(paymentRecordList.getUser().getId().intValue());
			aluminiRecordDto.setFirstName(paymentRecordList.getUser().getFirstName());
			aluminiRecordDto.setLastName(paymentRecordList.getUser().getLastName());
			aluminiRecordDto.setEmail(paymentRecordList.getUser().getEmail());
			aluminiRecordDto.setPhoneNumber(paymentRecordList.getUser().getPhoneNumber());

			AluminiRecord fetchrecord = new AluminiRecord();
			try {
				fetchrecord = aluminiRepository.findByuser(user).get();

			} catch (Exception e) {
			}
			aluminiRecordDto.setCompanyName(fetchrecord.getCompanyName());
			aluminiRecordDto.setPlacementStatus(fetchrecord.getPlacementStatus());
			aluminiRecordDto.setEmployementType(fetchrecord.getEmployementType());
			aluminiRecordDto.setAluminiDesignation(fetchrecord.getAluminiDesignation());
			aluminiRecordDto.setAluminiComments(fetchrecord.getAluminiComments());

			return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", aluminiRecordDto);
		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Use Not fetched", null);
		}
	}

	@PutMapping("/{id}")
	public ApiResponse<Void> update(@PathVariable int id, @RequestBody String clientDto) throws IOException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			AluminiRecordDto aluminiRecordDto = mapper.readValue(clientDto, AluminiRecordDto.class);

			User user = userRepository.findById(Long.valueOf(id)).get();

			AluminiRecord updatedRedord = new AluminiRecord();
			try {
				AluminiRecord deleteAluminiRecord = aluminiRepository.findByuser(user).get();
				updatedRedord = deleteAluminiRecord;
				updatedRedord.setCompanyName(aluminiRecordDto.getCompanyName());
				updatedRedord.setPlacementStatus(aluminiRecordDto.getPlacementStatus());
				updatedRedord.setEmployementType(aluminiRecordDto.getEmployementType());
				updatedRedord.setAluminiDesignation(aluminiRecordDto.getAluminiDesignation());
				updatedRedord.setAluminiComments(aluminiRecordDto.getAluminiComments());
				updatedRedord.setUser(user);

			} catch (Exception e) {
				updatedRedord.setCompanyName(aluminiRecordDto.getCompanyName());
				updatedRedord.setEmployementType(aluminiRecordDto.getEmployementType());
				updatedRedord.setPlacementStatus(aluminiRecordDto.getPlacementStatus());
				updatedRedord.setAluminiDesignation(aluminiRecordDto.getAluminiDesignation());
				updatedRedord.setAluminiComments(aluminiRecordDto.getAluminiComments());
				updatedRedord.setUser(user);

			}

			aluminiRepository.save(updatedRedord);
			return new ApiResponse<>(HttpStatus.OK.value(), "Alumini  Record Saved", null);

		} catch (Exception e) {
			return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Cannot Update IUser ", null);
		}
	}
}
