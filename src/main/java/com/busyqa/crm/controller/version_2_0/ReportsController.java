package com.busyqa.crm.controller.version_2_0;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busyqa.crm.mail.MailClient;
import com.busyqa.crm.message.response.ApiResponse;
import com.busyqa.crm.message.response.ResponseMessage;
import com.busyqa.crm.model.BusyqaCourse;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.PaymentRecord;
import com.busyqa.crm.model.ReportCourseDto;
import com.busyqa.crm.model.ReportDto;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reports")
public class ReportsController {

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

	@GetMapping("/getALL")
	public ApiResponse<ReportDto> getAllDetails() {

		ReportDto reportDto = new ReportDto();

		try{
			reportDto.setLeadsCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get()).size());

		}catch (Exception e){

		}

		try{
			reportDto.setStudentCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.STUDENT.name()).get()).size());

		}catch (Exception e){

		}

		try{
			reportDto.setInternCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.INTERN.name()).get()).size());
		}catch (Exception e){

		}

		try{
			reportDto.setMockCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.MOCK.name()).get()).size());
		}catch (Exception e){

		}

		try{
			reportDto.setResumeCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.RESUME.name()).get()).size());
		}catch (Exception e){

		}

		try{
			reportDto.setAluminiCount(paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.ALUMINI.name()).get()).size());
		}catch (Exception e){

		}

		return new ApiResponse<>(HttpStatus.OK.value(), "Reports  fetched successfully.", reportDto);
	}

	@GetMapping("/getCourseBy")
	public ApiResponse<List<ReportCourseDto>> getAllCourseByDetails() {
		List<BusyqaCourse> busyqaCourseList = busyqaCourseRepo.findAll();


		List<ReportCourseDto> reportCourseDtoList = new ArrayList<>();

		for (int i = 0; i < busyqaCourseList.size(); i++){
			ReportCourseDto reportCourseDto = new ReportCourseDto();
			reportCourseDto.setCourseName(busyqaCourseList.get(i).getCourseName());

			//Leads

			List<PaymentRecord> paymentRecordRepositoryList = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get());
			int couseLeadsCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList.size(); k ++ ){
				if (paymentRecordRepositoryList.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseLeadsCount = couseLeadsCount + 1;
				}
			}
			reportCourseDto.setCouseLeadsCount(couseLeadsCount);

			//Student

			List<PaymentRecord> paymentRecordRepositoryList2 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.STUDENT.name()).get());
			int couseStudentCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList2.size(); k ++ ){
				if (paymentRecordRepositoryList2.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseStudentCount = couseStudentCount + 1;
				}
			}
			reportCourseDto.setCouseStudentCount(couseStudentCount);

			//Intern

			List<PaymentRecord> paymentRecordRepositoryList3 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.INTERN.name()).get());
			int couseInternCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList3.size(); k ++ ){
				if (paymentRecordRepositoryList3.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseInternCount = couseInternCount + 1;
				}
			}
			reportCourseDto.setCouseInternCount(couseInternCount);

			//Mock

			List<PaymentRecord> paymentRecordRepositoryList4 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.MOCK.name()).get());
			int couseMockCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList4.size(); k ++ ){
				if (paymentRecordRepositoryList4.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseMockCount = couseMockCount + 1;
				}
			}
			reportCourseDto.setCouseMockCount(couseMockCount);


			//Resume

			List<PaymentRecord> paymentRecordRepositoryList5 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.RESUME.name()).get());
			int couseResumeCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList5.size(); k ++ ){
				if (paymentRecordRepositoryList5.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseResumeCount = couseResumeCount + 1;
				}
			}
			reportCourseDto.setCouseResumeCount(couseResumeCount);


			//Alumini

			List<PaymentRecord> paymentRecordRepositoryList6 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.ALUMINI.name()).get());
			int couseAluminitCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList6.size(); k ++ ){
				if (paymentRecordRepositoryList6.get(k).getBusyqaBatch().getBusyqaCourse().getCourseName().equals(busyqaCourseList.get(i).getCourseName())){
					couseAluminitCount = couseAluminitCount + 1;
				}
			}
			reportCourseDto.setCouseAluminitCount(couseAluminitCount);
			reportCourseDtoList.add(reportCourseDto);
		}


		return new ApiResponse<>(HttpStatus.OK.value(), "Reports  fetched successfully.", reportCourseDtoList);
	}


	@GetMapping("/getSeasonBy")
	public ApiResponse<List<ReportCourseDto>> getAllSeasonByDetails() {
		List<String> busyqaCourseList = new ArrayList<>();
		busyqaCourseList.add("spring");
		busyqaCourseList.add("summer");
		busyqaCourseList.add("fall");
		busyqaCourseList.add("winter");


		List<ReportCourseDto> reportCourseDtoList = new ArrayList<>();

		for (int i = 0; i < busyqaCourseList.size(); i++){
			ReportCourseDto reportCourseDto = new ReportCourseDto();
			reportCourseDto.setCourseName(busyqaCourseList.get(i));

			//Leads

			List<PaymentRecord> paymentRecordRepositoryList = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.LEADS.name()).get());
			int couseLeadsCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList.size(); k ++ ){
				if (paymentRecordRepositoryList.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseLeadsCount = couseLeadsCount + 1;
				}
			}
			reportCourseDto.setCouseLeadsCount(couseLeadsCount);

			//Student

			List<PaymentRecord> paymentRecordRepositoryList2 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.STUDENT.name()).get());
			int couseStudentCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList2.size(); k ++ ){
				if (paymentRecordRepositoryList2.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseStudentCount = couseStudentCount + 1;
				}
			}
			reportCourseDto.setCouseStudentCount(couseStudentCount);

			//Intern

			List<PaymentRecord> paymentRecordRepositoryList3 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.INTERN.name()).get());
			int couseInternCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList3.size(); k ++ ){
				if (paymentRecordRepositoryList3.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseInternCount = couseInternCount + 1;
				}
			}
			reportCourseDto.setCouseInternCount(couseInternCount);

			//Mock

			List<PaymentRecord> paymentRecordRepositoryList4 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.MOCK.name()).get());
			int couseMockCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList4.size(); k ++ ){
				if (paymentRecordRepositoryList4.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseMockCount = couseMockCount + 1;
				}
			}
			reportCourseDto.setCouseMockCount(couseMockCount);


			//Resume

			List<PaymentRecord> paymentRecordRepositoryList5 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.RESUME.name()).get());
			int couseResumeCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList5.size(); k ++ ){
				if (paymentRecordRepositoryList5.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseResumeCount = couseResumeCount + 1;
				}
			}
			reportCourseDto.setCouseResumeCount(couseResumeCount);


			//Alumini

			List<PaymentRecord> paymentRecordRepositoryList6 = paymentRecordRepository
					.findByClientStatus(clientStatusRepository.findByname(ClientStatusName.ALUMINI.name()).get());
			int couseAluminitCount = 0 ;
			for (int k = 0; k < paymentRecordRepositoryList6.size(); k ++ ){
				if (paymentRecordRepositoryList6.get(k).getBusyqaBatch().getSeason().equals(busyqaCourseList.get(i))){
					couseAluminitCount = couseAluminitCount + 1;
				}
			}
			reportCourseDto.setCouseAluminitCount(couseAluminitCount);
			reportCourseDtoList.add(reportCourseDto);
		}


		return new ApiResponse<>(HttpStatus.OK.value(), "Reports  fetched successfully.", reportCourseDtoList);
	}
}
