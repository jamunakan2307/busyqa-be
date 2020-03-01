package com.busyqa.crm.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.busyqa.crm.message.response.ApiResponse;
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
import com.busyqa.crm.security.services.FileService;
import com.busyqa.crm.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/resumes")
public class ResumeController {
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
	public ResumeController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/{id}")
	public ApiResponse<Void> handleFileUpload(@PathVariable String id, @RequestParam("file") MultipartFile file)
			throws IOException {
		try {
			User user = userRepository.findById(Long.valueOf(id)).get();
			if (file.getContentType().equals("application/pdf")) {
				fileService.storeResumeFile(file, user.getUsername() + "_Resume", ".pdf");
				return new ApiResponse<>(HttpStatus.OK.value(), "Resume... uploaded", null);
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
